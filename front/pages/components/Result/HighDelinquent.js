import React, { useState, useRef } from "react";
import { Tab, Tabs } from "react-bootstrap";
import { Button, Overlay, Popover, Container } from "react-bootstrap";
const HighDelinquent = () => {
  // 툴팁 제어
  const [show, setShow] = useState(false);
  const [target, setTarget] = useState(null);
  const ref = useRef(null);

  const handleClick = (event) => {
    setShow(!show);
    setTarget(event.target);
  };
  return (
    <>
      <Container>
        <div style={{ display: "flex", alignItems: "center" }}>
          <h2
            style={{
              paddingTop: "20px",
              marginRight: "10px",
              marginBottom: "20px",
            }}
          >
            고액·상습 체납자 명단
          </h2>
          <div ref={ref}>
            <Button
              style={{ marginTop: "10px" }}
              onClick={handleClick}
              variant="outline-primary"
              size="sm"
            >
              고액·상습 체납자??
            </Button>

            <Overlay
              show={show}
              target={target}
              placement="bottom"
              container={ref}
              containerPadding={20}
            >
              <Popover id="popover-contained">
                <Popover.Body>
                  체납기간 1년이상! 체납국세 2억이상! 고액·상습 체납자입니다
                </Popover.Body>
              </Popover>
            </Overlay>
          </div>
        </div>
        <Tabs
          defaultActiveKey="personal"
          id="fill-tab-example"
          className="mb-3"
          fill
        >
          <Tab eventKey="personal" title="개인">
            <iframe
              src="https://www.nts.go.kr/nts/ad/openInfo/selectList.do"
              title="External Page"
              width="100%"
              height="500px"
            />{" "}
          </Tab>
          <Tab eventKey="corporation" title="법인">
            <iframe
              src="https://www.nts.go.kr/nts/ad/openInfo/selectList.do?tcd=1"
              title="External Page"
              width="100%"
              height="500px"
            />{" "}
          </Tab>
        </Tabs>
        <div style={{ marginTop: "20px" }}>
          <p>1. 해당 명단은 고액·상습 체납자 명단이예요!</p>
          <p>
            2. 해당 명단에 없어도 세금 미납, 체납 내역이 있을 수도 있어요!!!
          </p>
          <p>
            3. 관할청의 <strong>미납조세 정보조회</strong>를 하면 보다 정확하게
            확인할 수 있어요!
            <br /> 개인정보의 문제로 본인이 아니면 확인하기 어려워요ㅠ
          </p>
        </div>
      </Container>
    </>
  );
};

export default HighDelinquent;
