import Head from "next/head";
import { Container } from "react-bootstrap";

import MainPage from "./MainPage";

export default function Home() {
  return (
    <>
      <Head>
        <title>어바웃홈</title>
        <meta
          name="description"
          content="우리집은 안전할까? 우리집 정보 확인하기"
        />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="icon" href="/favicon.ico" />
      </Head>
      <Container fluid className="p-0">
        <MainPage></MainPage>
      </Container>
    </>
  );
}
