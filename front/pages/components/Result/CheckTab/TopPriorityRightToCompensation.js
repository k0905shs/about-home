import React from "react";

const TopPriorityRightToCompensation = ({ parsedResult }) => {
  console.log("최우선변제권", parsedResult.address);

  return (
    <>
      <div>
        <h3 style={{ paddingTop: "20px", marginRight: "10px" }}>
          최우선변제권
        </h3>
      </div>
    </>
  );
};

export default TopPriorityRightToCompensation;
