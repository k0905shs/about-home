import { useRouter } from "next/router";
import { useEffect, useState } from "react";
import { Container, Tab, Tabs } from "react-bootstrap";

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

  const [response1, setResponse1] = useState(initialResponse1 || []);
  const [response2, setResponse2] = useState(initialResponse2 || []);
  const [response3, setResponse3] = useState(initialResponse3 || []);
  const [response4, setResponse4] = useState(initialResponse4 || []);
  const [result, setResult] = useState(initialResult || []);
  const [address, setAddress] = useState("");
  console.log("result", result);
  useEffect(() => {
    const handleBeforeUnload = (event) => {
      event.preventDefault();
      // 컴포넌트가 마운트될 때 클라이언트 로컬스토리지에서 값 가져오기
      if (typeof localStorage !== "undefined") {
        const storedResponse1 = localStorage.getItem("response1");
        const storedResponse2 = localStorage.getItem("response2");
        const storedResponse3 = localStorage.getItem("response3");
        const storedResponse4 = localStorage.getItem("response4");
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
        if (storedResponse4 && storedResponse4 !== "undefined") {
          setResponse4(storedResponse4);
        }
        if (storedResult && storedResult !== "undefined") {
          const parsedResult = JSON.parse(storedResult);
          setResult(parsedResult);
          setAddress(parsedResult.address);
        }
      }
      // 이벤트 메시지 설정
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
