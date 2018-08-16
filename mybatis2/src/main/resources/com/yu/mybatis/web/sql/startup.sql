DROP TABLE IF EXISTS sys_dict;
CREATE TABLE sys_dict (
  id    BIGINT(32)  NOT NULL AUTO_INCREMENT
  COMMENT '主键',
  code  VARCHAR(64) NOT NULL
  COMMENT '类别',
  name  VARCHAR(64) NOT NULL
  COMMENT '字典',
  value VARCHAR(64) NOT NULL
  COMMENT '字典值',
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 7
  DEFAULT CHARSET = utf8;

INSERT INTO sys_dict VALUES
  ('1', '性别', '男', '男'),
  ('2', '性别', '女', '女'),
  ('3', '季度', '第一季度', '1'),
  ('4', '季度', '第二季度', '2'),
  ('5', '季度', '第三季度', '3'),
  ('6', '季度', '第四季度', '4');
