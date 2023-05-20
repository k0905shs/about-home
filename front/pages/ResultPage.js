import { useRouter } from "next/router";
import { useEffect, useState } from "react";
import RealLandPrice from "./components/Result/RealLandPrice";
import OfficialLandPrice from "./components/Result/OfficialLandPrice";
import { Container, Tab, Tabs } from "react-bootstrap";
import HighDelinquent from "./components/Result/HighDelinquent";
import LongtermRent from "./components/Result/LongtermRent";

import CheckInfo from "./components/Result/CheckInfo";

const ResultPage = () => {
  const router = useRouter();
  const {
    response1: initialResponse1,
    response2: initialResponse2,
    response3: initialResponse3,
    result: initialResult,
  } = router.query;

  const [response1, setResponse1] = useState(initialResponse1);
  const [response2, setResponse2] = useState(initialResponse2);
  const [response3, setResponse3] = useState(initialResponse3);
  const [result, setResult] = useState(initialResult);

  useEffect(() => {
    // 컴포넌트가 마운트될 때 클라이언트 로컬스토리지에서 값 가져오기
    const storedResponse1 = localStorage.getItem("response1");
    const storedResponse2 = localStorage.getItem("response2");
    const storedResponse3 = localStorage.getItem("response3");
    const storedResult = localStorage.getItem("result");

    if (storedResponse1 && storedResponse1 !== "undefined") {
      setResponse1(storedResponse1);
    }
    if (storedResponse2 && storedResponse2 !== "undefined") {
      setResponse2(storedResponse2);
    }
    if (storedResponse3 && storedResponse3 !== "undefined") {
      setResponse3(storedResponse3);
    }
    if (storedResult && storedResult !== "undefined") {
      setResult(storedResult);
    }
  }, []);

  useEffect(() => {
    // response1, response2, response3 값이 변경될 때마다 클라이언트 로컬스토리지에 저장
    localStorage.setItem("response1", response1);
    localStorage.setItem("response2", response2);
    localStorage.setItem("response3", response3);
    localStorage.setItem("result", result);
  }, [response1, response2, response3, result]);

  console.log("result", result);

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
      <Tabs
        style={{ marginTop: "20px" }}
        defaultActiveKey="ResultPage"
        id="fill-tab-example"
        className="mb-3"
        fill
      >
        <Tab eventKey="ResultPage" title="공시지가 / 실거래가">
          <Container>
            <OfficialLandPrice response1={response1}></OfficialLandPrice>
            <Separator />
            <RealLandPrice response2={response2}></RealLandPrice>
            <Separator />
            <LongtermRent response3={response3}></LongtermRent>

            <CheckInfo></CheckInfo>
          </Container>
        </Tab>
        <Tab eventKey="HighDelinquent" title="고액 체납자 조회">
          <HighDelinquent></HighDelinquent>
        </Tab>
        <Tab eventKey="Result" title="결과">
          Tab content for Loooonger Tab
        </Tab>
      </Tabs>
    </>
  );
};

export default ResultPage;
