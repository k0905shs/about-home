import React from "react";
import { Container, Button } from "react-bootstrap";
import { useRouter } from "next/router";

const MainPage = () => {
  const router = useRouter();

  const handleSelect = () => {
    router.push("/CheckMyHome");
  };
  return (
    <Container style={{ minHeight: "80vh" }}>
      <div className="d-grid gap-2">
        <div style={{ height: "80vh", textAlign: "center" }}>이미지</div>
        <Button onClick={handleSelect} variant="primary" size="lg">
          우리집 진단하러 가기
        </Button>
      </div>
    </Container>
  );
};

export default MainPage;
