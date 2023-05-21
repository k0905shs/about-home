import React, { useState, useRef } from "react";
import { commaFormat } from "../../../utils/util";
import { Button, Overlay, Popover } from "react-bootstrap";

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

const OfficialLandPrice = ({ response1 }) => {
  let landPrice = [];

  try {
    if (typeof response1 === "string") {
      landPrice = JSON.parse(response1)?.data || [];
    }
  } catch (error) {
    console.error("Error parsing JSON:", error);
  }

  // 3자리수 콤마 포맷
  const formatPrice = (value) => {
    return value.toLocaleString();
  };

  // 그래프 툴팁
  const customTooltip = (price, name) => {
    if (name === "Line" || name === "Area") {
      return [null];
    }

    if (name === "Bar") {
      return [`금액: ${commaFormat(price)}원`];
    }

    return null;
  };

  //공시지가 툴팁
  const [show, setShow] = useState(false);
  const [target, setTarget] = useState(null);
  const ref = useRef(null);

  const handleClick = (event) => {
    setShow(!show);
    setTarget(event.target);
  };

  // 공시지가 데이터가 없을 경우
  if (landPrice.length === 0) {
    return (
      <>
        <h4 style={{ marginTop: "30px" }}>공시지가 데이터가 없습니다.</h4>
        <div>
          <h6>왜 공시지가 데이터가 없나요?</h6>
          <li>건물 유형을 확인해주세요!!</li>
          <li>신축 건물이면 아직 없을 수 있어요!</li>
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
      <div style={{ display: "flex", alignItems: "center" }}>
        <h2 style={{ paddingTop: "20px", marginRight: "10px" }}>공시지가</h2>

        <div ref={ref}>
          <Button
            style={{ marginTop: "10px" }}
            onClick={handleClick}
            variant="outline-primary"
            size="sm"
          >
            공시지가란?
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
                <strong>공시지가란?</strong> <br /> 국토교통부 장관이
                조사·평가하여 공시한 토지의 단위면적(㎡)당 가격.
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
          data={landPrice.landPriceInfoList}
          margin={{ top: 80, right: 40, bottom: 30, left: 40 }}
        >
          <XAxis
            dataKey="year"
            label={{ value: "년도", position: "bottom", offset: 0 }}
          />
          <YAxis
            type="number"
            label={{ value: "원(㎡)", offset: 30, angle: 0, position: "top" }}
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
          <Line type="monotone" dataKey="price" name="Line" stroke="#3399FF" />
        </ComposedChart>
      </ResponsiveContainer>
    </>
  );
};

export default OfficialLandPrice;
