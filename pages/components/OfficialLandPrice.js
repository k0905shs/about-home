import React from "react";
import { commaFormat } from "../../utils/util";

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

  const customTooltip = (price, name) => {
    if (name === "Line" || name === "Area") {
      return [null];
    }

    if (name === "Bar") {
      return [`금액: ${commaFormat(price)}원`];
    }

    return null;
  };

  if (landPrice.count === 0) {
    return <h5>공시지가 데이터가 없습니다.</h5>;
  }

  return (
    <>
      <h2 style={{ paddingTop: "20px" }}>공시지가</h2>
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
