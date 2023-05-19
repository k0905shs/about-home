import React, { useState, useEffect } from "react";
import styled from "styled-components";
import { CopyToClipboard } from "react-copy-to-clipboard";
import {
  FacebookShareButton,
  FacebookIcon,
  TwitterIcon,
  TwitterShareButton,
} from "react-share";
import { useScript } from "../../hooks";

// 제목과 버튼을 감싸는 컨테이너
const FlexContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

// 버튼을 배치시키는 컨테이너
const GridContainer = styled.div`
  display: grid;
  grid-template-columns: repeat(4, 48px);
  grid-column-gap: 8px;
  justify-content: center;
  align-items: center;
  margin-bottom: 16px;
`;

// Style을 적용한 버튼 컴포넌트 추가
const URLShareButton = styled.button`
  width: 48px;
  height: 48px;
  color: white;
  border-radius: 24px;
  border: 0px;
  font-weight: 500;
  font-size: 18px;
  cursor: pointer;
  background-color: #7362ff;
  &:hover {
    background-color: #a99fee;
  }
  padding: 0px;
`;

// 카카오버튼
const KakaoButtonWrapper = styled.div`
  display: flex;
`;

const KakaoShareButton = styled.a`
  cursor: pointer;
`;

const KakaoIcon = styled.img`
  width: 48px;
  height: 48px;
  border-radius: 24px;
`;

const Footer = () => {
  // window 객체에서 현재 url 가져오기
  const [currentUrl, setCurrentUrl] = useState("");

  // useEffect 내에서 window 객체를 사용하기 위해 useEffect 내부에서 처리해야 함
  useEffect(() => {
    if (typeof window !== "undefined") {
      setCurrentUrl(window.location.href);
    }
  }, []);

  // kakao SDK import하기
  const status = useScript(
    "https://t1.kakaocdn.net/kakao_js_sdk/2.1.0/kakao.min.js"
  );

  // kakao sdk 초기화하기
  // status가 변경될 때마다 실행되며, status가 ready일 때 초기화를 시도합니다.
  useEffect(() => {
    if (status === "ready" && typeof window !== "undefined" && window.Kakao) {
      if (!window.Kakao.isInitialized()) {
        window.Kakao.init("d3bc5655e50ada258ccb3361b6cb0e40");
      }
    }
  }, [status]);

  const handleKakaoButton = () => {
    window.Kakao.Share.sendScrap({
      requestUrl: currentUrl,
    });
  };

  return (
    <>
      <footer
        className="footer"
        style={{
          textAlign: "center",
          backgroundColor: "#ececec",
          paddingTop: "20px",
          marginTop: "20px",
        }}
      >
        <p> © 2023 cmoonjun11@gmail.com</p>
        <p> © 2023 k0905sh@kakao.com</p>
        <FlexContainer>
          <h5>공유하기</h5>
          <GridContainer>
            <FacebookShareButton url={currentUrl}>
              <FacebookIcon
                size={48}
                round={true}
                borderRadius={24}
              ></FacebookIcon>
            </FacebookShareButton>
            <TwitterShareButton url={currentUrl}>
              <TwitterIcon
                size={48}
                round={true}
                borderRadius={24}
              ></TwitterIcon>
            </TwitterShareButton>
            <CopyToClipboard text={currentUrl}>
              <URLShareButton>URL</URLShareButton>
            </CopyToClipboard>
            <KakaoButtonWrapper>
              <KakaoShareButton onClick={handleKakaoButton}>
                <KakaoIcon src="/assets/kakaoIcon1.png"></KakaoIcon>
              </KakaoShareButton>
            </KakaoButtonWrapper>
          </GridContainer>
        </FlexContainer>
      </footer>
    </>
  );
};

export default Footer;
