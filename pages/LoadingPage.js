import React from "react";
import { Container } from "react-bootstrap";
import { ClipLoader } from "react-spinners";

const LoadingPage = () => {
  return (
    <>
      <Head>
        <title>어바웃홈 </title>
        <meta charset="utf-8" />
        <meta name="referrer" content="always" />
        <meta name="description" content="어바웃홈" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="icon" href="/favicon.ico" />
        <meta name="keywords" content="어바웃홈 about-home" />
        <meta name="robots" content="index,follow" />
        <meta property="og:site_name" content="어바웃홈 about-home" />
        <meta property="og:title" content="어바웃홈 about-home" />
        <meta property="og:description" content="어바웃홈 about-home" />
        <meta property="og:url" content="https://about-home.net" />
        <meta name="twitter:card" content="summary" />
        <meta name="twitter:title" content="어바웃홈 about-home" />
        <meta name="twitter:description" content="어바웃홈 about-home" />
      </Head>
      <Container
        style={{
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
          minHeight: "100vh",
          flexDirection: "column",
        }}
      >
        <h2>잠시만 기다려주세요.</h2>
        <ClipLoader
          color="rgba(8, 136, 255, 1)"
          loading
          size={50}
          speedMultiplier={0.5}
        />
      </Container>
    </>
  );
};

export default LoadingPage;
