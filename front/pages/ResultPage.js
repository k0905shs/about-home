import { useRouter } from "next/router";
import RealLandPrice from "./components/Result/RealLandPrice";
import OfficialLandPrice from "./components/Result/OfficialLandPrice";
import { Container, Tab, Tabs } from "react-bootstrap";
import HighDelinquent from "./components/Result/HighDelinquent";
import LongtermRent from "./components/Result/LongtermRent";

import CheckInfo from "./components/Result/CheckInfo";

const ResultPage = () => {
  const router = useRouter();
  const { response1, response2, response3 } = router.query;

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
            <RealLandPrice response2={response2}></RealLandPrice>
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
