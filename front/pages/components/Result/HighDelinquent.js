import React, { useState, useRef } from "react";
import { Tab, Tabs } from "react-bootstrap";
import { Button, Overlay, Popover } from "react-bootstrap";
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
    </>
  );
};

export default HighDelinquent;
