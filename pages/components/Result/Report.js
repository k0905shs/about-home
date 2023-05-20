import React from "react";

const Report = ({ result }) => {
  let parsedResult = {};
  console.log();
  if (typeof result === "string") {
    parsedResult = JSON.parse(result) || [];
  }

  console.log(parsedResult.address);

  return <>{result}</>;
};

export default Report;
