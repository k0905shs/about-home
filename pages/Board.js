import React from "react";
import BoardList from "./components/BulletinBoard/BoardList";
import { Container } from "react-bootstrap";
import Head from "next/head";

const Board = () => {
  return (
    <>
      <Head>
        <title>어바웃홈 - 공지사항</title>
        <meta charset="utf-8" />
        <meta name="referrer" content="always" />
        <meta name="description" content="어바웃홈, 공지사항" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="icon" href="/favicon.ico" />
        <meta name="keywords" content="어바웃홈 about-home" />
        <meta name="robots" content="index,follow" />
        <meta property="og:site_name" content="어바웃홈 about-home, 공지사항" />
        <meta property="og:title" content="어바웃홈 about-home, 공지사항" />
        <meta
          property="og:description"
          content="어바웃홈 about-home, 공지사항"
        />
        <meta property="og:url" content="https://about-home.net" />
        <meta name="twitter:card" content="summary" />
        <meta name="twitter:title" content="어바웃홈 about-home" />
        <meta name="twitter:description" content="어바웃홈 about-home" />
      </Head>
      <Container style={{ minHeight: "80vh" }}>
        <BoardList></BoardList>
      </Container>
    </>
  );
};

export default Board;
