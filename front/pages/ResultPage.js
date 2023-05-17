import { useRouter } from "next/router";
import RealLandPrice from "./components/RealLandPrice";
import OfficialLandPrice from "./components/OfficialLandPrice";

const ResultPage = () => {
  const router = useRouter();
  const { response1, response2, result } = router.query;

  return (
    <>
      <OfficialLandPrice response1={response1}></OfficialLandPrice>
      <RealLandPrice response2={response2}></RealLandPrice>
      {result}
    </>
  );
};

export default ResultPage;
