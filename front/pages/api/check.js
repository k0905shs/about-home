import axios from "axios";

export default function handler(req, res) {
  console.log("req", req);
  if (req.method === "POST") {
    const { result } = req.body;
    console.log("result", result);

    // 추출한 값을 사용하여 POST 요청을 보냅니다.
    const postData = {
      buildingCode: result.buildingcode,
      address: result.address,
      searchYear: 5,
    };
    console.log("postData", postData);
    axios
      .post("http://172.30.1.93:9999/home/check-land-price", postData)
      .then((response) => {
        console.log("aaaaaaa", response.data);
        if (response.status === 200) {
          console.log("요청 성공");
          res.status(200).json(response.data);
        }
        // POST 요청 성공 시 응답을 클라이언트에게 반환합니다.
      })
      .catch((error) => {
        // POST 요청 실패 시 에러를 클라이언트에게 반환합니다.
        res
          .status(500)
          .json({ message: "POST 요청 실패", error: error.message });
      });
  } else {
    // POST 요청 외에 다른 HTTP 메소드로의 요청은 허용되지 않습니다.
    console.log("요청에 실패했습니다.");
  }
}
