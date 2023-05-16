import React from "react";
import { Container } from "react-bootstrap";
import { ClipLoader } from "react-spinners";

const LoadingPage = () => {
  return (
    <>
      <Container
        style={{
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
          minHeight: "100vh",
          flexDirection: "column",
        }}
      >
        <h2>잠시만 기다려주세요.</h2>
        <ClipLoader
          color="rgba(8, 136, 255, 1)"
          loading
          size={50}
          speedMultiplier={0.5}
        />
      </Container>
    </>
  );
};

export default LoadingPage;
