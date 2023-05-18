import React, { useState, useRef } from "react";
import { commaFormat } from "../../../utils/util";
import { Button, Container, Overlay, Popover } from "react-bootstrap";

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
  let landPrice = [];
  let realPriceData = [];

  // response2 받은 데이터를 리차트 형식에 맞게 변환해주는 함수
  const getData = (landPrice) => {
    for (let i = 0; i < landPrice.length; i++) {
      let realAveragePrice = 0;
      if (landPrice[i].totalPrice !== 0) {
        const date = landPrice[i].date.substring(0, 6);
        realAveragePrice =
          Number(landPrice[i].totalPrice) / Number(landPrice[i].count);
        const averagePriceFormatted = realAveragePrice.toFixed(0);

        realPriceData.push({ date, price: averagePriceFormatted });
      }
    }
  };

  try {
    if (typeof response2 === "string") {
      landPrice = JSON.parse(response2)?.data || [];
      getData(landPrice);
    }
  } catch (error) {
    console.error("Error parsing JSON:", error);
  }

  // 3자리수 콤마 포맷
  const formatPrice = (value) => {
    return value.toLocaleString();
  };

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
              label={{ value: "년/월", position: "bottom", offset: 0 }}
              interval={1}
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

        <div>
          <h5>평균 실거래가 참고사항</h5>
          <p>
            1. 평균 실거래가는 단순한 평균 값이며, 개별 매매 거래 가격은 상이할
            수 있습니다.
            <br /> 따라서 실제 거래 시 가격 협상이 이루어지며, 매매 가격은
            실거래가보다 낮거나 높을 수 있습니다.
          </p>
          <p>
            2. 평균 실거래가는 시장의 경향을 파악하는 데 도움을 줄 수 있지만,
            개별 지번의 가치를 판단하는 데에는 절대적인 기준이 아닙니다.
          </p>
        </div>
      </Container>
    </>
  );
};

export default RealLandPrice;
