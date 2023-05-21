import React from "react";
import { Container, Button } from "react-bootstrap";
import { useRouter } from "next/router";
import Image from "next/image";

const MainPage = () => {
  const router = useRouter();

  const handleSelect = () => {
    router.push("/CheckMyHome");
  };

  return (
    <div className="d-grid gap-2">
      <div style={{ textAlign: "center" }}>
        <Image
          src="/assets/main/main1.png"
          alt="어바웃홈1"
          layout="responsive"
          width={500}
          height={300}
        />
        <Image
          src="/assets/main/main2.png"
          alt="어바웃홈2"
          layout="responsive"
          width={500}
          height={300}
        />
        <Image
          src="/assets/main/main3.png"
          alt="어바웃홈3"
          layout="responsive"
          width={500}
          height={300}
        />
        <Image
          src="/assets/main/main5.png"
          alt="어바웃홈4"
          layout="responsive"
          width={500}
          height={300}
        />
      </div>
      <Button
        onClick={handleSelect}
        variant="primary"
        size="lg"
        style={{ height: "60px", fontSize: "22px" }}
      >
        우리집 진단하러 가기
      </Button>
    </div>
  );
};

export default MainPage;
