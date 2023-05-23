import { useRouter } from "next/router";
import { useEffect, useState } from "react";
import { Container, Tab, Tabs } from "react-bootstrap";
import Head from "next/head";

//page
import RealLandPrice from "./components/Result/RealLandPrice";
import OfficialLandPrice from "./components/Result/OfficialLandPrice";
import HighDelinquent from "./components/Result/HighDelinquent";
import LongtermRent from "./components/Result/LongtermRent";
import CheckInfo from "./components/Result/CheckInfo";
import Report from "./components/Result/Report";

const ResultPage = () => {
  const router = useRouter();

  const {
    response1: initialResponse1,
    response2: initialResponse2,
    response3: initialResponse3,
    response4: initialResponse4,
    result: initialResult,
  } = router.query;

  const [response1] = useState(initialResponse1 || []);
  const [response2] = useState(initialResponse2 || []);
  const [response3] = useState(initialResponse3 || []);
  const [response4] = useState(initialResponse4 || []);
  const [result] = useState(initialResult || []);
  const [address, setAddress] = useState("");

  useEffect(() => {
    const handleBeforeUnload = (event) => {
      // 이벤트 메시지 설정
      event.preventDefault();
      event.returnValue = ""; // 크로스 브라우징을 위한 반환값 설정
    };

    // beforeunload 이벤트 핸들러를 window 객체에 추가합니다.
    window.addEventListener("beforeunload", handleBeforeUnload);

    // 컴포넌트가 언마운트될 때 이벤트 핸들러를 제거합니다.
    return () => {
      window.removeEventListener("beforeunload", handleBeforeUnload);
    };
  }, []);

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
      <Head>
        <title>어바웃홈 - 진단결과</title>
        <meta charset="utf-8" />
        <meta name="referrer" content="always" />
        <meta name="description" content="어바웃홈, 진단결과" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="icon" href="/favicon.ico" />
        <meta name="keywords" content="어바웃홈 about-home" />
        <meta name="robots" content="index,follow" />
        <meta property="og:site_name" content="어바웃홈 about-home, 진단결과" />
        <meta property="og:title" content="어바웃홈 about-home, 진단결과" />
        <meta
          property="og:description"
          content="어바웃홈 about-home, 진단결과"
        />
        <meta property="og:url" content="https://about-home.net" />
        <meta name="twitter:card" content="summary" />
        <meta name="twitter:title" content="어바웃홈 about-home" />
        <meta name="twitter:description" content="어바웃홈 about-home" />
      </Head>
      <Tabs
        style={{ marginTop: "20px" }}
        defaultActiveKey="ResultPage"
        id="fill-tab-example"
        className="mb-3"
        fill
      >
        <Tab eventKey="ResultPage" title="공시지가 / 실거래가">
          <Container>
            <h2 style={{ marginTop: "40px" }}>{address}</h2>
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
        <Tab eventKey="Check" title="확인사항">
          <Report result={result} response4={response4}></Report>
        </Tab>
      </Tabs>
    </>
  );
};

export default ResultPage;
