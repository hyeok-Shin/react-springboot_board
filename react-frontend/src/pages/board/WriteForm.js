import React, { useState } from 'react';
import Button from 'react-bootstrap/Button';
import { Form } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import uuid from 'react-uuid';

const WriteForm = () => {
  const [post, setPost] = useState({
    title: '',
    content: '',
    layer: 0,
    originNo: '',
    filePath: '',
    fileNAme: '',
    originFileName: '',
  });
  const [file, setFile] = useState('');

  const formData = new FormData();

  const onSaveFiles = (e) => {
    setFile(e.target.files[0]);
    setPost({ ...post, originFileName: e.target.files[0].name });
  };

  const changeValue = (e) => {
    setPost({
      ...post,
      [e.target.name]: e.target.value, //동적으로 키값 생성
    });
    // console.log(post);
  };

  const navigate = useNavigate(); //history

  const submitPost = (e) => {
    e.preventDefault(); // 기본 action 막음
    formData.append('post', new Blob([JSON.stringify(post)], { type: 'application/json' }));
    formData.append('file', file);

    fetch('http://localhost:8080/post', {
      method: 'POST',
      cache: 'no-cache',
      headers: {},
      body: formData,
    })
      .then((res) => {
        if (res.status === 201) {
          return res.json();
        } else {
          return null;
        }
      })
      .then((res) => {
        if (res !== null) {
          navigate('/');
        } else {
          alert('게시글 작성 실패');
        }
      });
  };

  // 허용가능한 확장자 목록!
  // const ALLOW_FILE_EXTENSION = "jpg,jpeg,png";
  // const FILE_SIZE_MAX_LIMIT = 5 * 1024 * 1024;  // 5MB

  // const [file, setFile] = useState<File>();

  // const handleChangeFile = (e) => {
  //   console.log(e.target.files);
  //   setPost({...post, [e.target.name]: e.target.files})
  //   console.log(post)
  // }

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
        <br />
        <Form.Group controlId="formFileMultiple" className="mb-3">
          <Form.Label>첨부파일</Form.Label>
          <Form.Control type="file" name="upload_file" multiple onChange={onSaveFiles} />
        </Form.Group>
      </Form.Group>
      <Button variant="primary" type="submit">
        작성
      </Button>
    </Form>
  );
};

export default WriteForm;
