import React, { useState, useRef } from "react";
import { commaFormat } from "../../../utils/util";
import {
  Button,
  Container,
  Overlay,
  Popover,
  Modal,
  Table,
} from "react-bootstrap";

import {
  ResponsiveContainer,
  AreaChart,
  Line,
  Area,
  XAxis,
  YAxis,
  ComposedChart,
  Tooltip,
  Legend,
  CartesianGrid,
  Bar,
} from "recharts";

const LongtermRent = ({ response3 }) => {
  //평균 실거래가, response3 받은 데이터를 리차트 형식에 맞게 변환
  let landPrice = [];
  try {
    if (typeof response3 === "string") {
      landPrice = JSON.parse(response3)?.data || [];
    }
  } catch (error) {
    console.error("Error parsing JSON:", error);
  }

  const LongtermRentStackData = [];

  // landPrice 배열을 기반으로 LongtermStackData 배열 구성
  landPrice.forEach((item) => {
    const { date, buildingSaleInfoList } = item;

    buildingSaleInfoList.forEach((building) => {
      const { deposit, rentPrice, area, floor, saleDate } = building;
      const saleDateParts = saleDate.split("-");
      const month = parseInt(saleDateParts[0], 10);
      const day = parseInt(saleDateParts[1], 10);

      const formattedSaleDate = `${month}월 ${day}일`;

      if (rentPrice === 0) {
        LongtermRentStackData.push({
          date,
          deposit,
          rentPrice,
          area,
          floor,
          saleDate: formattedSaleDate,
        });
      }
    });
  });

  // LongtermRentStackData 배열을 date로 그룹화하여 deposit 평균 계산
  const LongtermRentAverageData = [];

  const groupedData = LongtermRentStackData.reduce((acc, item) => {
    const { date, deposit } = item;

    const year = date.substring(0, 4); // 년도 추출
    const month = date.substring(4, 6); // 월 추출
    const quarter = Math.ceil(Number(month) / 3); // 분기 계산

    const key = `${year}.${quarter}분기`; // 분기를 포함한 키 생성

    if (!acc[key]) {
      acc[key] = {
        totalDeposit: 0,
        count: 0,
      };
    }

    acc[key].totalDeposit += deposit;
    acc[key].count++;

    return acc;
  }, {});

  // 평균 계산하여 LongtermRentAverageData 배열에 추가
  for (const key in groupedData) {
    const { totalDeposit, count } = groupedData[key];

    const averageDeposit = (totalDeposit / count).toFixed(0);

    LongtermRentAverageData.push({
      quarter: key,
      averageDeposit,
    });
  }

  // 년, 월 노출 시키는 함수
  const getCurrentYearAndMonth = () => {
    const currentDate = new Date();
    const year = currentDate.getFullYear();
    const month = (currentDate.getMonth() + 1).toString().padStart(2, "0");
    return `${year}.${month}`;
  };

  //평균전세가 툴팁
  const [show, setShow] = useState(false);
  const [target, setTarget] = useState(null);
  const ref = useRef(null);

  const handleClick = (event) => {
    setShow(!show);
    setTarget(event.target);
  };

  // 3자리수 콤마 포맷
  const formatPrice = (value) => {
    return value.toLocaleString();
  };

  // 리차트 커스텀 툴팁
  const customTooltip = (price, name) => {
    if (name === "Line" || name === "Area") {
      return [null];
    }

    if (name === "Bar") {
      return [`금액: ${commaFormat(price)}만원`];
    }

    return null;
  };

  // 모달창 제어
  const [showModal, setShowModal] = useState(false);
  const handleClose = () => setShowModal(false);
  const handleShow = () => setShowModal(true);

  // 실거래가 없을때
  if (LongtermRentAverageData.length === 0) {
    return (
      <>
        <h4 style={{ marginTop: "10px" }}>전세 실거래 데이터가 없습니다.</h4>
        <div>
          <h6>왜 실거래 데이터가 없나요?</h6>
          <li>건물 유형을 확인해주세요</li>
          <li>
            거래량이 많지 않는 다세대주택(공동주택, 도시형생활 주택 등) /
            단독주택 / 다가구 주택은 실거래 데이터가 없을 수 있어요
          </li>
          <li>주소랑 건물, 제대로 입력했는데 안나오면 거래량이 없는거예요!</li>
        </div>
      </>
    );
  }

  return (
    <>
      <Container>
        <div style={{ display: "flex", alignItems: "center" }}>
          <h2 style={{ paddingTop: "20px", marginRight: "10px" }}>
            평균 전세가
          </h2>

          <div ref={ref}>
            <Button
              style={{ marginTop: "10px" }}
              onClick={handleClick}
              variant="outline-primary"
              size="sm"
            >
              평균 전세가란?
            </Button>

            <Overlay
              show={show}
              target={target}
              placement="bottom"
              container={ref}
              containerPadding={20}
            >
              <Popover id="popover-contained">
                <Popover.Body>
                  <strong>평균 전세기란?</strong> <br /> 신고된 가격으로 계산한
                  해당 년의 평균 전세가
                </Popover.Body>
              </Popover>
            </Overlay>
          </div>
        </div>
        <div style={{ marginLeft: "auto", fontSize: "14px", color: "gray" }}>
          {getCurrentYearAndMonth()} 국토교통부 기준
        </div>

        <ResponsiveContainer width="100%" height={300}>
          <ComposedChart
            data={LongtermRentAverageData}
            margin={{ top: 100, right: 40, bottom: 30, left: 40 }}
          >
            <XAxis
              dataKey="quarter"
              label={{ value: "분기", position: "bottom", offset: 0 }}
            />
            <YAxis
              type="number"
              label={{ value: "만원", offset: 30, angle: 0, position: "top" }}
              tickFormatter={formatPrice}
            />
            <CartesianGrid stroke="#f5f5f5" />
            <Tooltip formatter={customTooltip} />
            <Area
              type="monotone"
              dataKey="averageDeposit"
              fill="#CCE5FF"
              stroke="#8884d8"
              name="Area"
            />
            <Bar
              dataKey="averageDeposit"
              name="Bar"
              barSize={20}
              fill="#0080FF"
            />
            <Line
              type="monotone"
              dataKey="averageDeposit"
              name="Line"
              stroke="#3399FF"
            />
          </ComposedChart>
        </ResponsiveContainer>

        <div style={{ display: "flex", alignItems: "center" }}>
          <h5 style={{ paddingTop: "10px", marginRight: "10px" }}>
            전세가 확인하기
          </h5>
          <Button variant="outline-primary" onClick={handleShow} size="sm">
            전세가 확인
          </Button>

          <Modal show={showModal} onHide={handleClose}>
            <Modal.Header closeButton>
              <Modal.Title>실거래가</Modal.Title>
              <div
                style={{
                  marginLeft: "10px",
                  fontSize: "14px",
                  color: "gray",
                }}
              >
                {getCurrentYearAndMonth()} 국토교통부 기준
              </div>
            </Modal.Header>
            <Modal.Body>
              <Table striped bordered hover>
                <thead>
                  <tr>
                    <th>계약월</th>
                    <th>전세가</th>
                  </tr>
                </thead>
                <tbody>
                  {LongtermRentStackData.map((price, index) => (
                    <tr key={index}>
                      <td>{price.date}</td>
                      <td>
                        {commaFormat(price.deposit)}만원, {price.saleDate},{" "}
                        {price.floor}층, 전용면적 {price.area}(㎡)
                      </td>
                    </tr>
                  ))}
                </tbody>
              </Table>
            </Modal.Body>
            <Modal.Footer>
              <Button variant="secondary" onClick={handleClose}>
                Close
              </Button>
            </Modal.Footer>
          </Modal>
        </div>
      </Container>
    </>
  );
};

export default LongtermRent;
