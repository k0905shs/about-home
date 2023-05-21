import React from "react";
import BoardList from "./components/BulletinBoard/BoardList";
import { Container } from "react-bootstrap";

const Board = () => {
  return (
    <>
      <Container style={{ minHeight: "80vh" }}>
        <BoardList></BoardList>
      </Container>
    </>
  );
};

export default Board;
