import Button from 'react-bootstrap/Button';
import Container from 'react-bootstrap/Container';
import Form from 'react-bootstrap/Form';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';
import {Link, useNavigate} from 'react-router-dom';
import {getElement} from "bootstrap/js/src/util";
import {useState} from "react";

function Header() {

  const [keyword, setKeyword] = useState('');
  const navigate = useNavigate();

  const changeValue = (e) => {
    setKeyword(e.target.value);
  }

  const search = (e) => {
    e.preventDefault()
    navigate('/', {
      state: {
        keyword: keyword,
      }
    });

  }

  function enterkey() {
    if (window.event.keyCode == 13) {
      navigate('/', {
        state: {
          keyword: keyword,
        }
      });
    }
  }




  return (
    <Navbar bg="dark" variant="dark" expand="lg">
      <Container fluid>
        <Link to="/" className="navbar-brand">
          게시판
        </Link>
        <Navbar.Toggle aria-controls="navbarScroll" />
        <Navbar.Collapse id="navbarScroll">
          <Nav className="me-auto my-2 my-lg-0" style={{ maxHeight: '100px' }} navbarScroll>
            <Link to="/writeForm" className="nav-link">
              글쓰기
            </Link>{' '}
            <Link to="/" className="nav-link">
              글목록
            </Link>
            {/*<NavDropdown title="회원" id="navbarScrollingDropdown">*/}
            {/*  <Link to="/joinForm" className="dropdown-item">회원가입</Link>*/}
            {/*  <Link to="/loginForm" className="dropdown-item">로그인</Link>*/}
            {/*  <NavDropdown.Divider />*/}
            {/*  <NavDropdown.Item href="#action5">Something else here</NavDropdown.Item>*/}
            {/*</NavDropdown>*/}
          </Nav>
          <Form className="d-flex" onSubmit={search}>
            <Form.Control type="search" placeholder="Search" className="me-2" aria-label="Search" onChange={changeValue} name="searchKeyword"/>
            <Button variant="outline-success" type="submit" onKeyUp={enterkey}>Search</Button>
          </Form>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}

export default Header;
