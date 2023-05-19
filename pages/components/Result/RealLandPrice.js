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

const RealLandPrice = ({ response2 }) => {
  //평균 실거래가, response2 받은 데이터를 리차트 형식에 맞게 변환
  let landPrice = [];
  let realPriceData = [];

  const getData = (landPrice) => {
    for (let i = 0; i < landPrice.length; i++) {
      let realAveragePrice = 0;
      if (landPrice[i].totalPrice !== 0) {
        const date = `${landPrice[i].date.substring(2, 4)}.${landPrice[
          i
        ].date.substring(4, 6)}`;
        realAveragePrice =
          Number(landPrice[i].totalPrice) / Number(landPrice[i].count);
        const averagePriceFormatted = realAveragePrice.toFixed(0);

        realPriceData.push({ date, price: averagePriceFormatted });
      }
    }
  };

  // 누적 실거래가, 변환
  let realPiceStackData = [];

  const getStackData = (landPrice) => {
    for (let i = 0; i < landPrice.length; i++) {
      let stackData = landPrice[i].buildingSaleInfoList;
      if (landPrice[i].totalPrice !== 0) {
        const date = `${landPrice[i].date.substring(0, 4)}.${landPrice[
          i
        ].date.substring(4, 6)}`;

        stackData.forEach((building) => {
          const saleDateParts = building.saleDate.split("-");
          const month = parseInt(saleDateParts[0], 10);
          const day = parseInt(saleDateParts[1], 10);

          const formattedSaleDate = `${month}월 ${day}일`;

          realPiceStackData.push({
            date,
            price: building.price,
            saleDate: formattedSaleDate,
            area: building.area,
            floor: building.floor,
          });
        });
      }
    }
  };

  try {
    if (typeof response2 === "string") {
      landPrice = JSON.parse(response2)?.data || [];
      getData(landPrice);
      getStackData(landPrice);
    }
  } catch (error) {
    console.error("Error parsing JSON:", error);
  }

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

  //실거래가가 툴팁
  const [show, setShow] = useState(false);
  const [target, setTarget] = useState(null);
  const ref = useRef(null);

  const handleClick = (event) => {
    setShow(!show);
    setTarget(event.target);
  };

  // 모달창 제어
  const [showModal, setShowModal] = useState(false);
  const handleClose = () => setShowModal(false);
  const handleShow = () => setShowModal(true);

  // 실거래가 없을때
  if (realPriceData.length === 0) {
    return (
      <>
        <h4 style={{ marginTop: "10px" }}>실거래 데이터가 없습니다.</h4>
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

  // 년, 월 노출 시키는 함수
  const getCurrentYearAndMonth = () => {
    const currentDate = new Date();
    const year = currentDate.getFullYear();
    const month = (currentDate.getMonth() + 1).toString().padStart(2, "0");
    return `${year}.${month}`;
  };

  return (
    <>
      <Container>
        <div style={{ display: "flex", alignItems: "center" }}>
          <h2 style={{ paddingTop: "20px", marginRight: "10px" }}>
            평균 실거래가
          </h2>

          <div ref={ref}>
            <Button
              style={{ marginTop: "10px" }}
              onClick={handleClick}
              variant="outline-primary"
              size="sm"
            >
              실거래가란?
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
                  <strong>실거래가란?</strong> <br /> 부동산의 실거래가를 비롯한
                  계약 내용에 의해 신고된 가격
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
            data={realPriceData}
            margin={{ top: 100, right: 40, bottom: 30, left: 40 }}
          >
            <XAxis
              dataKey="date"
              label={{ value: "년.월", position: "bottom", offset: 0 }}
              interval={3}
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
              dataKey="price"
              fill="#CCE5FF"
              stroke="#8884d8"
              name="Area"
            />
            <Bar dataKey="price" name="Bar" barSize={20} fill="#0080FF" />
            <Line
              type="monotone"
              dataKey="price"
              name="Line"
              stroke="#3399FF"
            />
          </ComposedChart>
        </ResponsiveContainer>

        <div style={{ display: "flex", alignItems: "center" }}>
          <h5 style={{ paddingTop: "10px", marginRight: "10px" }}>
            평균 실거래가 참고사항
          </h5>
          <Button variant="outline-primary" onClick={handleShow} size="sm">
            실거래가 확인
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
                    <th>매매가</th>
                  </tr>
                </thead>
                <tbody>
                  {realPiceStackData.map((price, index) => (
                    <tr key={index}>
                      <td>{price.date}</td>
                      <td>
                        {commaFormat(price.price)}만원, {price.saleDate},{" "}
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

export default RealLandPrice;
