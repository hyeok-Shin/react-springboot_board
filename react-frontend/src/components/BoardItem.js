import React from 'react';
import { Card } from 'react-bootstrap';
import { Link } from 'react-router-dom';

const BoardItem = (props) => {
  const { id, title, layer } = props.post; //구조 분해 할당
  return (
    <Card className="box">
      <Card.Body>
        <Card.Title> {title}</Card.Title>
        <Link to={'/post/' + id} className="btn btn-secondary">
          상세보기
        </Link>{' '}
        <Link
          to={'/reply/writeForm/' + id}
          state={{ id: id, layer: layer }}
          className="btn btn-secondary"
        >
          답글쓰기
        </Link>{' '}
        <Link
          to={'/reply/' + id + '/' + (layer + 1)}
          state={{ id: id, layer: layer }}
          className="btn btn-secondary"
        >
          답글보기
        </Link>
      </Card.Body>
    </Card>
  );
};

export default BoardItem;
