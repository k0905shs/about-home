import React from "react";
import { Container } from "react-bootstrap";
import TopPriorityRightToCompensation from "./CheckTab/TopPriorityRightToCompensation";
import CheckList from "./CheckTab/CheckList";

const Report = ({ result, response4 }) => {
  let parsedResult = {};
  if (typeof result === "string") {
    parsedResult = JSON.parse(result) || [];
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
          response4={response4}
        ></TopPriorityRightToCompensation>
        <Separator />

        <CheckList parsedResult={parsedResult}></CheckList>
      </Container>
    </>
  );
};

export default Report;
