import { useRouter } from "next/router";
import RealLandPrice from "./components/Result/RealLandPrice";
import OfficialLandPrice from "./components/Result/OfficialLandPrice";
import { Container } from "react-bootstrap";

const ResultPage = () => {
  const router = useRouter();
  const { response1, response2, result } = router.query;

  return (
    <>
      <Container>
        <OfficialLandPrice response1={response1}></OfficialLandPrice>
        <RealLandPrice response2={response2}></RealLandPrice>
      </Container>
    </>
  );
};

export default ResultPage;
