import React, { useState } from "react";
import { Container, Stack, Form, Button } from "react-bootstrap";
import DaumPostcode from "react-daum-postcode";

const CheckMyHome = () => {
  const [inputVal, setInputVal] = useState({
    contract: "",
    deposit: "",
    rentalFee: "",
    purchaser: "",
    provider: "",
  });

  const [result, setResult] = useState([]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setInputVal({ ...inputVal, [name]: value });
  };

  const handleRadioChange = (e) => {
    const { name, value } = e.target;
    setInputVal({ ...inputVal, [name]: value });
  };

  const [postcode, setPostcode] = useState("");
  const [address, setAddress] = useState("");
  const [detailAddress, setDetailAddress] = useState("");
  const [showDaumPostcode, setShowDaumPostcode] = useState(false);

  const handleOpenPostcode = () => {
    setShowDaumPostcode(!showDaumPostcode);
  };

  const handleComplete = (data) => {
    let fullAddress = data.address;
    let extraAddress = "";
    if (data.addressType === "R") {
      if (data.bname !== "") {
        extraAddress += data.bname;
      }
      if (data.buildingName !== "") {
        extraAddress +=
          extraAddress !== "" ? `, ${data.buildingName}` : data.buildingName;
      }
      fullAddress += extraAddress !== "" ? ` (${extraAddress})` : "";
    }
    console.log(extraAddress); // e.g. '서울 성동구 왕십리로2길 20 (성수동1가)'

    setPostcode(data.zonecode);
    setAddress(extraAddress);
  };

  return (
    <>
      <Container style={{ width: "80%" }}>
        <h2 style={{ marginTop: "10px" }}>우리집 진단하기</h2>
        <p style={{ fontWeight: "bold" }}>
          진단을 하려면 해당 주소의 등기부등본이 먼저 필요해요!
        </p>
        <section
          style={{
            display: "flex",
            justifyContent: "space-around",
          }}
        >
          <p>
            <a href="">등기부등본 뽑으러 가기</a>
          </p>
          <p>
            <a href="">등기부등본을 뽑을줄 모른다면?</a>
          </p>
        </section>
        <section>
          <Stack gap={2}>
            <Form>
              <Form.Group
                className="mb-3"
                controlId="exampleForm.ControlInput1"
              >
                <div
                  style={{
                    fontWeight: "500",
                    marginTop: "10px",
                    fontSize: "20px",
                  }}
                >
                  계약: <br />
                  <input
                    type="radio"
                    name="contract"
                    value="monthlyRent"
                    checked={inputVal.contract === "monthlyRent"}
                    onChange={handleRadioChange}
                  />{" "}
                  월세
                  <input
                    style={{ marginLeft: "10px" }}
                    type="radio"
                    name="contract"
                    value="yearlyRent"
                    checked={inputVal.contract === "yearlyRent"}
                    onChange={handleRadioChange}
                  />
                  전세
                </div>
                <Form.Label>
                  <h5 style={{ marginTop: "20px" }}>보증금</h5>
                </Form.Label>
                <Form.Control
                  type="number"
                  name="deposit"
                  min="0"
                  required
                  value={inputVal.deposit}
                  onChange={handleInputChange}
                />
                <Form.Label>
                  <h5 style={{ marginTop: "20px" }}>소재지</h5>
                </Form.Label>{" "}
                <Button type="button" onClick={handleOpenPostcode}>
                  검색
                </Button>
                {showDaumPostcode && (
                  <div>
                    <DaumPostcode
                      onComplete={handleComplete}
                      autoClose={true}
                      width={500}
                      height={600}
                      animation
                    />
                  </div>
                )}
                <Form.Control
                  type="text"
                  placeholder="우편번호"
                  value={postcode}
                  readOnly
                />
                <div>
                  <Form.Control
                    type="text"
                    placeholder="주소"
                    value={address}
                    readOnly
                  />
                </div>
                <div>
                  <Form.Control
                    type="text"
                    placeholder="상세주소"
                    value={detailAddress}
                    onChange={(e) => setDetailAddress(e.target.value)}
                  />
                </div>
                <Form.Label>
                  <h5 style={{ marginTop: "20px" }}>월 임차료</h5>
                </Form.Label>
                <Form.Control
                  type="number"
                  name="weight"
                  min="0"
                  required
                  value={inputVal.rentalFee}
                  onChange={handleInputChange}
                />
                <Form.Label>
                  <h5 style={{ marginTop: "20px" }}>갑구 </h5>
                </Form.Label>
                <Form.Control
                  type="text"
                  name="purchaser"
                  value={inputVal.purchaser}
                  required
                  onChange={handleInputChange}
                />
                <Form.Label>
                  <h5 style={{ marginTop: "20px" }}>을구 </h5>
                </Form.Label>
                <Form.Control
                  type="text"
                  name="provider"
                  value={inputVal.provider}
                  required
                  onChange={handleInputChange}
                />
                <Button
                  style={{
                    width: "100%",
                    marginBottom: "20px",
                    marginTop: "30px",
                  }}
                >
                  계산하기
                </Button>
              </Form.Group>
            </Form>
          </Stack>
        </section>
      </Container>
    </>
  );
};

export default CheckMyHome;
