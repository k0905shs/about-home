// pages/result.js
import { useEffect } from "react";
import { useRouter } from "next/router";

const ResultPage = () => {
  const router = useRouter();
  const { landPriceInfoList } = router.query;

  return (
    <>
      <div>{landPriceInfoList}</div>
    </>
  );
};

export default ResultPage;
