import React from "react";
import { Table, Container } from "react-bootstrap";

const BoardList = () => {
  return (
    <>
      <h2 style={{ marginTop: "20px" }}>공지사항</h2>
      <Container>
        <Table striped bordered hover style={{ textAlign: "center" }}>
          <thead>
            <tr>
              <th style={{ width: "10%" }}>번호</th>
              <th style={{ width: "60%" }}>제목</th>
              <th style={{ width: "10%" }}>작성자</th>
              <th style={{ width: "10%" }}>날짜</th>
              <th style={{ width: "10%" }}>조회수</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>1</td>
              <td>Mark</td>
              <td>dsadsadsa</td>
              <td>dsadas</td>
              <td>@mdo</td>
            </tr>
          </tbody>
        </Table>
      </Container>
    </>
  );
};

export default BoardList;
