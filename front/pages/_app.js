import "bootstrap/dist/css/bootstrap.min.css";
import Header from "../src/Layout/Header";
import Footer from "../src/Layout/Footer";
import { Container } from "react-bootstrap";
import SSRProvider from "react-bootstrap/SSRProvider";

function App({ Component, pageProps }) {
  return (
    <>
      <SSRProvider>
        <Header></Header>
        <Container>
          <Component {...pageProps} />
        </Container>
        <Footer></Footer>
      </SSRProvider>
    </>
  );
}

export default App;
