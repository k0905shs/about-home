import React from "react";
import { Table, Accordion } from "react-bootstrap";
import { commaFormat } from "@/utils/util";

const TopPriorityRightToCompensation = ({ response4 }) => {
  let result = [];
  try {
    if (typeof response4 === "string") {
      result = JSON.parse(response4)?.data || [];
    }
  } catch (error) {
    console.error("Error parsing JSON:", error);
  }
  console.log(result);

  return (
    <>
      <div>
        <h3
          style={{
            paddingTop: "20px",
            marginRight: "10px",
            marginBottom: "30px",
          }}
        >
          최우선변제권
        </h3>

        <Accordion defaultActiveKey="0" alwaysOpen>
          <Accordion.Item eventKey="0">
            <Accordion.Header>
              <h4>
                담보물권설정일 <br />
                {result.startDate} ~{" "}
                {result.endDate === "9999-12-31" ? "현재" : result.endDate}
              </h4>
            </Accordion.Header>
            <Accordion.Body>
              <Table>
                <thead>
                  <tr>
                    <th>지역</th>
                    <th>소액보증금 범위</th>
                    <th>최우선변제금액</th>
                  </tr>
                </thead>
                <tbody>
                  {result.policyList.map((arr, index) => (
                    <tr key={index}>
                      <td>{arr.region}</td>
                      <td>{commaFormat(arr.deposit)}만원 이하</td>
                      <td>{commaFormat(arr.repayAmount)}만원</td>
                    </tr>
                  ))}
                </tbody>
              </Table>
            </Accordion.Body>
          </Accordion.Item>
        </Accordion>
      </div>
    </>
  );
};

export default TopPriorityRightToCompensation;
