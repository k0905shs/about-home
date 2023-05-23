import { Container, Nav, Navbar } from "react-bootstrap";
import React from "react";
import { useRouter } from "next/router";

const Header = () => {
  const router = useRouter();

  const handleSelect = (eventKey) => {
    router.push(eventKey);
  };

  return (
    <Navbar bg="primary" variant="dark" expand="lg" onSelect={handleSelect}>
      <Container>
        <Navbar.Brand href="/" style={{ marginLeft: "10px" }}>
          어바웃홈
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav" style={{ marginLeft: "15px" }}>
          <Nav className="me-auto">
            <Nav.Link eventKey="/Board">공지사항</Nav.Link>
            <Nav.Link eventKey="/CheckMyHome">진단하기</Nav.Link>
            {/* <Nav.Link eventKey="/">부동산 정보</Nav.Link> */}
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
};

export default Header;
