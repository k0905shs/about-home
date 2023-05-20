import React, { useState } from "react";
import {
  Container,
  Stack,
  Form,
  Button,
  Collapse,
  Badge,
} from "react-bootstrap";
import DaumPostcode from "react-daum-postcode";
import axios from "axios";
import { useRouter } from "next/router";

import LoadingPage from "./LoadingPage";

const CheckMyHome = () => {
  const router = useRouter();

  const [open, setOpen] = useState(false);

  const [inputVal, setInputVal] = useState({
    contract: "monthlyRent",
    deposit: "",
    rentalFee: "",
    purchaser: [],
    provider: [],
    collateral: "",
  });

  const [postcode, setPostcode] = useState("");
  const [address, setAddress] = useState("");
  const [detailAddress, setDetailAddress] = useState("");
  const [showDaumPostcode, setShowDaumPostcode] = useState(false);
  const [buildingcode, setBuildingcode] = useState("");
  const [jibun, setJibun] = useState("");

  const [building, setBuilding] = useState("APART");

  const [result, setResult] = useState({});

  const [isLoading, setIsLoading] = useState(false);

  const [validated, setValidated] = useState(false);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setInputVal({ ...inputVal, [name]: value });
  };

  const handleRadioChange = (e) => {
    const { name, value } = e.target;
    setInputVal({ ...inputVal, [name]: value });
  };

  const handleBuildingChange = (e) => {
    setBuilding(e.target.value);
  };

  const handleOpenPostcode = () => {
    setShowDaumPostcode(!showDaumPostcode);
  };

  // 갑구 체크박스
  const handleCheckboxChangePurchaser = (e) => {
    const { name, value, checked } = e.target;
    setInputVal((prevInputVal) => {
      let updatedPurchaser;
      if (checked) {
        // 선택한 체크박스가 아직 배열에 없는 경우 추가
        updatedPurchaser = [...prevInputVal.purchaser, value];
      } else {
        // 선택한 체크박스가 배열에 있는 경우 제거
        updatedPurchaser = prevInputVal.purchaser.filter(
          (item) => item !== value
        );
      }
      return {
        ...prevInputVal,
        [name]: updatedPurchaser,
      };
    });
  };

  //을구 체크박스
  const handleCheckboxChangeProvider = (e) => {
    const { name, value, checked } = e.target;
    setInputVal((prevInputVal) => {
      let updatedProvider;
      if (checked) {
        // 선택한 체크박스가 아직 배열에 없는 경우 추가
        updatedProvider = [...prevInputVal.provider, value];
      } else {
        // 선택한 체크박스가 배열에 있는 경우 제거
        updatedProvider = prevInputVal.provider.filter(
          (item) => item !== value
        );
      }
      return {
        ...prevInputVal,
        [name]: updatedProvider,
      };
    });
  };

  // 카카오주소 API
  const handleComplete = (data) => {
    let fullAddress = data.address;
    let extraAddress = "";
    let jibunAddress = "";
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

    // 지번 찾는 함수
    const getJibun = (address) => {
      const commaIndex = address.indexOf(",");
      return address.substring(0, commaIndex);
    };

    console.log(data.userSelectedType);
    if (data.userSelectedType === "J") {
      jibunAddress = getJibun(data.jibunAddressEnglish);
    } else if (data.autoJibunAddressEnglish) {
      jibunAddress = getJibun(data.autoJibunAddressEnglish);
    } else if (data.jibunAddressEnglish) {
      jibunAddress = getJibun(data.jibunAddressEnglish);
    }

    // 주소에 필요한 값 set
    setJibun(jibunAddress);
    setBuildingcode(data.buildingCode);
    setPostcode(data.zonecode);
    setAddress(extraAddress);
  };

  // 진단하기
  const handleSubmit = async (e) => {
    const form = e.currentTarget;
    console.log(form);
    if (form.checkValidity() === false) {
      e.preventDefault();
      e.stopPropagation();
    }

    setValidated(true);

    const newResult = {
      contract: inputVal.contract,
      deposit: inputVal.deposit,
      rentalFee: inputVal.rentalFee,
      purchaser: inputVal.purchaser,
      provider: inputVal.provider,
      collateral: inputVal.collateral,
      postcode: postcode,
      buildingcode: buildingcode,
      address: address,
      detailAddress: detailAddress,
      building: building,
      jibun: jibun,
    };

    // result에 새로운 값을 추가합니다.
    setResult(newResult);
    // 결과를 콘솔에 출력합니다.
    if (form.checkValidity()) {
      try {
        // API 호출을 수행하고 응답을 기다립니다.
        setIsLoading(true);
        const res = await axios.post("/api/check", {
          result: newResult,
        });

        setIsLoading(false);

        router.push(
          {
            pathname: "/ResultPage",
            query: {
              response1: JSON.stringify(res.data.response1),
              response2: JSON.stringify(res.data.response2),
              response3: JSON.stringify(res.data.response3),
              result: JSON.stringify(newResult),
            },
          },
          "/ResultPage",
          { shallow: true }
        );
      } catch (error) {
        // API 호출 중에 오류가 발생한 경우, 에러를 처리합니다.
        setIsLoading(false);
        console.error(error);
        alert("서버 오류로 나중에 다시 시도해주세요!");
      }
    }
  };

  return (
    <>
      {isLoading ? (
        <LoadingPage />
      ) : (
        <Container style={{ width: "80%" }}>
          <h2 style={{ marginTop: "10px" }}>우리집 진단하기</h2>
          <div style={{ fontWeight: "bold" }}>
            진단을 하려면 해당 주소의{" "}
            <a
              onClick={() => setOpen(!open)}
              aria-controls="example-collapse-text"
              aria-expanded={open}
            >
              <Badge pill bg="danger">
                등기부등본
              </Badge>{" "}
              이 먼저 필요해요!
            </a>
            <Collapse in={open}>
              <div id="example-collapse-text" style={{ marginTop: "10px" }}>
                <a href="http://www.iros.go.kr/PMainJ.jsp" target="_blank">
                  뽑으러 가기
                </a>
                <br />
                <a href="">뽑을줄 모른다면?</a>
              </div>
            </Collapse>
          </div>
          <section>
            <Stack gap={2}>
              <Form noValidate validated={validated} onSubmit={handleSubmit}>
                <Form.Group
                  className="mb-3"
                  controlId="exampleForm.ControlInput1"
                >
                  <div
                    style={{
                      fontWeight: "500",
                      marginTop: "30px",
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
                    <h5 style={{ marginTop: "20px" }}>월 임차료</h5>
                  </Form.Label>
                  <Form.Control
                    type="number"
                    name="rentalFee"
                    min="0"
                    required
                    value={inputVal.rentalFee}
                    onChange={handleInputChange}
                  />
                  <div
                    style={{
                      fontWeight: "500",
                      marginTop: "10px",
                      fontSize: "20px",
                    }}
                  >
                    건물 종류: <br />
                    <Form.Select
                      style={{ marginTop: "10px" }}
                      value={building}
                      onChange={handleBuildingChange}
                    >
                      <option value="APART">아파트</option>
                      <option value="HOUSE">
                        다세대주택 / 연립주택 / 빌라 / 도시형 생활주택
                      </option>
                      <option value="OFFICETEL">오피스텔</option>
                      <option value="TOWNHOUSE">단독주택 / 다가구주택</option>
                    </Form.Select>
                  </div>
                  <Form.Label>
                    <h5 style={{ marginTop: "20px" }}>소재지</h5>
                  </Form.Label>{" "}
                  <Button type="button" onClick={handleOpenPostcode} size="sm">
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
                      required
                    />
                  </div>
                  <Form.Label>
                    <h5 style={{ marginTop: "20px" }}>갑구 </h5>
                  </Form.Label>
                  <Form.Group>
                    <Form.Check
                      type="checkbox"
                      name="purchaser"
                      label="가처분"
                      value="injunction"
                      checked={inputVal.purchaser.includes("injunction")}
                      onChange={handleCheckboxChangePurchaser}
                      inline
                    />
                    <Form.Check
                      type="checkbox"
                      name="purchaser"
                      label="가등기"
                      value="provisionalRegistration"
                      checked={inputVal.purchaser.includes(
                        "provisionalRegistration"
                      )}
                      onChange={handleCheckboxChangePurchaser}
                      inline
                    />
                    <Form.Check
                      type="checkbox"
                      name="purchaser"
                      label="가압류 / 압류"
                      value="provisionalSeizure"
                      checked={inputVal.purchaser.includes(
                        "provisionalSeizure"
                      )}
                      onChange={handleCheckboxChangePurchaser}
                      inline
                    />
                    <Form.Check
                      type="checkbox"
                      name="purchaser"
                      label="신탁"
                      value="trust"
                      checked={inputVal.purchaser.includes("trust")}
                      onChange={handleCheckboxChangePurchaser}
                      inline
                    />
                  </Form.Group>
                  <Form.Label>
                    <h5 style={{ marginTop: "20px" }}>을구 </h5>
                  </Form.Label>
                  <Form.Group>
                    <Form.Check
                      type="checkbox"
                      name="provider"
                      label="임차권"
                      value="leasehold"
                      checked={inputVal.provider.includes("leasehold")}
                      onChange={handleCheckboxChangeProvider}
                      inline
                    />

                    <Form.Check
                      type="checkbox"
                      name="provider"
                      label="전세권"
                      value="LongtermRent"
                      checked={inputVal.provider.includes("LongtermRent")}
                      onChange={handleCheckboxChangeProvider}
                      inline
                    />
                  </Form.Group>
                  <Form.Label>
                    <h5 style={{ marginTop: "20px" }}>근저당</h5>
                  </Form.Label>
                  <Form.Control
                    type="number"
                    name="collateral"
                    min="0"
                    required
                    value={inputVal.collateral}
                    onChange={handleInputChange}
                  />
                  <Button
                    style={{
                      width: "100%",
                      marginBottom: "20px",
                      marginTop: "30px",
                    }}
                    type="submit"
                  >
                    진단하기
                  </Button>
                </Form.Group>
              </Form>
            </Stack>
          </section>
        </Container>
      )}
    </>
  );
};

export default CheckMyHome;
