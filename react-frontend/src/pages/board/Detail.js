import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import Button from 'react-bootstrap/Button';

const Detail = () => {
  const { id } = useParams(); //params
  const navigate = useNavigate(); //history

  const [post, setPost] = useState({
    id: '',
    title: '',
    content: '',
  });

  useEffect(() => {
    fetch('http://localhost:8080/post/' + id)
      .then((res) => res.json())
      .then((res) => {
        setPost(res);
      });
  }, []);

  const deletePost = () => {
    fetch('http://localhost:8080/post/' + id +'?fileName=' + encodeURI(post.fileName), {
      method: 'DELETE',
    })
      .then((res) => res.text())
      .then((res) => {
        if (res === 'ok') {
          navigate('/');
        } else {
          alert('삭제 실패');
        }
      });
  };

  const updatePost = () => {
    navigate('/updateForm/' + id);
  };

  const downloadFile = (url) => {
    const path = post.filePath + '/' + post.fileName;
    const originFileName = post.originFileName;
    fetch('http://localhost:8080/download?fileName=' + encodeURI(post.fileName))
      .then((res) => {
        return res.blob();
      })
      .then((blob) => {
        const url = window.URL.createObjectURL(blob);
        console.log(url)
        const a = document.createElement('a');
        a.href = url;
        a.download = post.originFileName;
        document.body.appendChild(a);
        a.click();
        setTimeout(() => {
          window.URL.revokeObjectURL(url);
        }, 60000);
        a.remove();
      })
        .catch(err => {
            console.log('err', err);
        })
  };

  return (
    <div>
      <h1>{post.title}</h1>
      <Button variant="warning" onClick={updatePost}>
        수정
      </Button>{' '}
      <Button variant="danger" onClick={deletePost}>
        삭제
      </Button>
      <br />
      <br />
        {post.fileName?<div><h5>첨부파일</h5>
            <Button variant="outline-success" onClick={downloadFile}>
        {post.originFileName}
            </Button></div> : null}
      <hr />
      <h3>{post.content}</h3>
    </div>
  );
};

export default Detail;
