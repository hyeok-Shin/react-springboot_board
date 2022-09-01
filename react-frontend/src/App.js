import React from 'react';
import { Container } from 'react-bootstrap';
import { Route, Routes } from 'react-router-dom';
import Header from './components/Header';
import WriteForm from './pages/board/WriteForm';
import Detail from './pages/board/Detail';
import LoginForm from './pages/user/LoginForm';
import Home from './pages/board/Home';
import JoinForm from './pages/user/JoinForm';
import UpdateForm from './pages/board/UpdateForm';
import './/App.css';
import ReplyWriteForm from "./pages/board/ReplyWriteForm";
import ReplyList from "./pages/board/ReplyList";

function App() {
  return (
    <div>
      <Header />
      <Container>
        <Routes>
          <Route path="/" exact={true} element={<Home />} />
          <Route path="/reply/:originNo/:layer" exact={true} element={<ReplyList />} />
          <Route path="/writeForm" exact={true} element={<WriteForm />} />
          <Route path="/reply/writeForm/:id" exact={true} element={<ReplyWriteForm />} />
          <Route path="/post/:id" exact={true} element={<Detail />} />
          <Route path="/loginForm" exact={true} element={<LoginForm />} />
          <Route path="/joinForm" exact={true} element={<JoinForm />} />
          <Route path="/updateForm/:id" exact={true} element={<UpdateForm />} />
        </Routes>
      </Container>
    </div>
  );
}

export default App;
