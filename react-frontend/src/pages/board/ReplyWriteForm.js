import React, { useState } from 'react';
import Button from 'react-bootstrap/Button';
import { Form } from 'react-bootstrap';
import {useLocation, useNavigate} from "react-router-dom";

const ReplyWriteForm = () => {
  const reply = "RE: "
  const location = useLocation()
  const no = location.state.id;
  const layer = location.state.layer;
  const [post, setPost] = useState({
    title: '',
    content: '',
    layer: layer+1,
    originNo: no,
  });

  const changeValue = (e) => {
    setPost({
      ...post,
      [e.target.name]: reply + e.target.value //동적으로 키값 생성
    });
  };

  const navigate = useNavigate(); //history

  const submitPost = (e) => {
    e.preventDefault(); // 기본 action 막음
    fetch('http://localhost:8080/post', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json; charset=utf-8',
      },
      body: JSON.stringify(post),
    })
      .then((res) => {
        if(res.status === 201) {
          return res.json();
        } else {
          return null;
        }

      })
      .then((res) => {
        if(res !== null) {
          navigate('/');
        } else {
          alert("게시글 작성 실패")
        } 
      });
  };

  return (
    <Form onSubmit={submitPost}>
      <Form.Group className="mb-3" controlId="formBasicEmail">
        <Form.Label>제목</Form.Label>
        <Form.Control
          type="text"
          placeholder="제목을 입력하세요."
          onChange={changeValue}
          name="title"
        />
      </Form.Group>
      <Form.Group className="mb-3" controlId="formBasicEmail">
        <Form.Label>내용</Form.Label>
        <Form.Control
          type="text"
          placeholder="내용을 입력하세요."
          onChange={changeValue}
          name="content"
        />
      </Form.Group>
      <Button variant="primary" type="submit">
        작성
      </Button>
    </Form>
  );
};

export default ReplyWriteForm;
