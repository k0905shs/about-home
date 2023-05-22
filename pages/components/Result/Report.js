import React from "react";
import { Container } from "react-bootstrap";
import TopPriorityRightToCompensation from "./CheckTab/TopPriorityRightToCompensation";
import CheckList from "./CheckTab/CheckList";

const Report = ({ result, response4 }) => {
  let parsedResult = {};
  if (typeof result === "string") {
    parsedResult = JSON.parse(result) || [];
  }

  let parsedResult2 = [];
  try {
    if (typeof response4 === "string") {
      parsedResult2 = JSON.parse(response4)?.data || [];
    }
  } catch (error) {
    console.error("Error parsing JSON:", error);
  }

  const Separator = () => {
    return (
      <hr
        style={{
          border: "1px solid gray",
          marginTop: "40px",
          marginBottom: "20px",
        }}
      />
    );
  };

  return (
    <>
      <Container>
        <div>
          <h2 style={{ paddingTop: "20px", marginRight: "10px" }}>확인사항</h2>
        </div>
        <TopPriorityRightToCompensation
          parsedResult={parsedResult}
          parsedResult2={parsedResult2}
        ></TopPriorityRightToCompensation>
        <Separator />

        <CheckList parsedResult={parsedResult}></CheckList>
      </Container>
    </>
  );
};

export default Report;
