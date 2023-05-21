import React from "react";
import { Accordion } from "react-bootstrap";

const CheckList = ({ parsedResult }) => {
  if (!parsedResult) {
    return {
      parsedResult: {
        purchaser: [],
        provider: [],
      },
    };
  }
  console.log("aa", parsedResult);

  const modifiedPurchaser =
    parsedResult &&
    parsedResult.purchaser &&
    Array.isArray(parsedResult.purchaser)
      ? parsedResult.purchaser.map((item) => {
          if (item === "trust") {
            return "신탁";
          } else if (item === "provisionalSeizure") {
            return "가압류 / 압류";
          } else if (item === "injunction") {
            return "가처분";
          } else if (item === "provisionalRegistration") {
            return "가등기";
          }
          // 모든 경우에 값을 반환하도록 추가
          return null;
        })
      : [];
  let strPurchaser = modifiedPurchaser.join(", ");

  const modifiedProvider =
    parsedResult &&
    parsedResult.provider &&
    Array.isArray(parsedResult.provider)
      ? parsedResult.provider.map((item) => {
          if (item === "leasehold") {
            return "임차권";
          } else if (item === "LongtermRent") {
            return "전세권";
          }
          // 모든 경우에 값을 반환하도록 추가
          return null;
        })
      : [];

  let strProvider = modifiedProvider.join(", ");

  return (
    <>
      <div>
        <h3 style={{ paddingTop: "20px" }}>권리사항</h3>
      </div>
      <div>
        <h4 style={{ paddingTop: "20px" }}>갑구(소유권에 관한 사항)</h4>
      </div>
      <section style={{ paddingTop: "20px" }}>
        <Accordion defaultActiveKey={parsedResult.purchaser} alwaysOpen>
          <Accordion.Item eventKey="injunction">
            <Accordion.Header>가처분이란?</Accordion.Header>
            <Accordion.Body>
              소유권 이전 다툼(소송)이 있을 떄 부동산의 처분 행위를 하지 못하게
              막는 제도
            </Accordion.Body>
          </Accordion.Item>
          <Accordion.Item eventKey="provisionalRegistration">
            <Accordion.Header>가등기란?</Accordion.Header>
            <Accordion.Body>
              이중매매, 소유권 이전 방해 등을 사전에 막아 원활한 소유권 이전을
              위해 등기 순위를 확보하는 제도
            </Accordion.Body>
          </Accordion.Item>
          <Accordion.Item eventKey="provisionalSeizure">
            <Accordion.Header>가압류 / 압류란?</Accordion.Header>
            <Accordion.Body>
              <strong>가압류</strong> : 채권자가 채무자에게 재산을 처분하거나
              은닉하지 못하도록 재산에 대한 처분권을 잠정적으로 박탈시키는 제도{" "}
              <br />
              <strong>압류</strong> : 판결문을 받은 후 상대방의 재산 처분을
              못하게 묶어두거나, 조세 미납 등의 이유로 국가기관이 부동산의
              처분행위를 못하게 막는 제도
            </Accordion.Body>
          </Accordion.Item>
          <Accordion.Item eventKey="trust">
            <Accordion.Header>신탁이란?</Accordion.Header>
            <Accordion.Body>
              부동산 신탁사가 부동산 자산을 신탁받아 소유권을 보유하고 있는 것!
              <br />
              신탁기간 중 임대차 계약을 할 경우, 세입자는 모든 임대차계약을
              집주인이 아닌 수탁자와 해야 함. 따라서 신탁등기는 권리관계가
              복잡해서 각별한 주의 필요!
            </Accordion.Body>
          </Accordion.Item>
        </Accordion>
      </section>
      <h5 style={{ marginTop: "30px" }}>
        해당 등기의 갑구에{" "}
        <strong style={{ color: "red" }}>{strPurchaser}</strong>
        {modifiedPurchaser.length > 0
          ? " 내용이 있어요!!! 정확한 권리사항 확인이 필요해 보여요!!"
          : "특이사항이 없어요!"}
      </h5>
      <div>
        <h4 style={{ paddingTop: "30px" }}>
          을구(소유권 이외의 권리에 관한 사항)
        </h4>
      </div>
      <section style={{ paddingTop: "20px" }}>
        <Accordion defaultActiveKey={parsedResult.provider} alwaysOpen>
          <Accordion.Item eventKey="leasehold">
            <Accordion.Header>임차권이란?</Accordion.Header>
            <Accordion.Body>
              상대방에게 목적물을 사용 및 수익할 것을 약정하고, 임차료를 지급할
              것을 약정함으로서 생기는 권리
              <br />
              여기서 주의해야할 건 <strong>임차권등기명령</strong> <br />
              <br />
              임차인이 임대차가 종료되었음에도 보증금을 돌려받지 못하고 이사를
              가게 되면 종전에 취득하였던 대항력 및 우선변제권이 상실되므로
              보증금을 돌려받기 어려워지게 됨. 이러한 문제를 해결하기 위해
              임차권등기명령제도는 법원의 집행명령에 따른 등기를 마치면
              임차인에게 대항력 및 우선변제권을 유지하게 하면서 임차주택에서
              자유롭게 이사할 수 있게 하는 제도
            </Accordion.Body>
          </Accordion.Item>
          <Accordion.Item eventKey="LongtermRent">
            <Accordion.Header>전세권이란?</Accordion.Header>
            <Accordion.Body>
              전세권은 부동산을 임대받는 사람이 일정 기간 동안 해당 부동산을
              사용하고 이용하는 권리
            </Accordion.Body>
          </Accordion.Item>
        </Accordion>
      </section>
      <h5 style={{ marginTop: "30px" }}>
        해당 등기의 을구에{" "}
        <strong style={{ color: "red" }}>{strProvider}</strong>
        {modifiedProvider.length > 0
          ? " 내용이 있어요!!! 정확한 권리사항 확인이 필요해 보여요!!"
          : "특이사항이 없어요!"}
      </h5>
    </>
  );
};

CheckList.getInitialProps = async () => {
  // 데이터를 가져오는 로직을 구현합니다.

  return {
    parsedResult: parsedResult || {}, // parsedResult가 없을 경우 빈 객체로 설정
  };
};

export default CheckList;
