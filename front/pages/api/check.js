import axios from "axios";

export default function handler(req, res) {
  console.log("req", req);
  if (req.method === "POST") {
    const { result } = req.body;
    console.log("result", result);

    // 추출한 값을 사용하여 POST 요청을 보냅니다.
    const officialLandPrice = {
      buildingCode: result.buildingcode,
      jibun: result.jibun,
      searchYear: 5,
    };

    const realLandPrice = {
      buildingType: result.building,
      buildingCode: result.buildingcode,
      jibun: result.jibun,
      searchMonth: 24,
    };

    const request1 = axios.post(
      "http://211.218.1.46:28080/home/check-land-price",
      officialLandPrice
    );
    const request2 = axios.post(
      "http://211.218.1.46:28080/home/check-building-sale",
      realLandPrice
    );

    Promise.allSettled([request1, request2])
      .then(([response1, response2]) => {
        if (response1.status === "fulfilled") {
          console.log("공시지가 요청 성공", response1.value.data);
        } else {
          console.log("공시지가 요청 실패", response1.reason);
        }

        if (response2.status === "fulfilled") {
          console.log("실거래가 요청 성공", response2.value.data);
        } else {
          console.log("실거래가 요청 실패", response2.reason);
        }

        const responseData1 =
          response1.status === "fulfilled" ? response1.value.data : null;
        const responseData2 =
          response2.status === "fulfilled" ? response2.value.data : null;

        res.json({ response1: responseData1, response2: responseData2 });
      })
      .catch((error) => {
        res.status(500).json({ message: "요청 실패", error: error.message });
      });
  } else {
    // POST 요청 외에 다른 HTTP 메소드로의 요청은 허용되지 않습니다.
    console.log("서버 요청에 실패했습니다.");
  }
}
