# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table company (
  c_id                      bigint auto_increment not null,
  c_site_status             integer,
  c_name                    varchar(255),
  c_logo                    varchar(255),
  c_slogan                  TEXT,
  c_city                    varchar(255),
  c_country                 varchar(255),
  c_about                   TEXT,
  c_facebook_address        varchar(255),
  c_twitter_address         varchar(255),
  c_linkedin_address        varchar(255),
  c_googleplus_address      varchar(255),
  c_web_address             varchar(255),
  c_email_address           varchar(255),
  c_real_address            varchar(255),
  c_phone                   varchar(255),
  c_title                   TEXT,
  c_site_description        TEXT,
  c_site_keywords           TEXT,
  constraint pk_company primary key (c_id))
;

create table image_future_graphic (
  id                        bigint auto_increment not null,
  file_name                 varchar(255),
  file_type                 varchar(255),
  size                      integer,
  is_active                 tinyint(1) default 0,
  created_at                datetime,
  modified_at               datetime,
  constraint pk_image_future_graphic primary key (id))
;

create table image_icon (
  id                        bigint auto_increment not null,
  file_name                 varchar(255),
  file_type                 varchar(255),
  size                      integer,
  is_active                 tinyint(1) default 0,
  created_at                datetime,
  modified_at               datetime,
  constraint pk_image_icon primary key (id))
;

create table image_phone (
  id                        bigint auto_increment not null,
  file_name                 varchar(255),
  file_type                 varchar(255),
  size                      integer,
  is_active                 tinyint(1) default 0,
  created_at                datetime,
  modified_at               datetime,
  product_id                bigint,
  constraint pk_image_phone primary key (id))
;

create table image_promo_graphic (
  id                        bigint auto_increment not null,
  file_name                 varchar(255),
  file_type                 varchar(255),
  size                      integer,
  is_active                 tinyint(1) default 0,
  created_at                datetime,
  modified_at               datetime,
  constraint pk_image_promo_graphic primary key (id))
;

create table image_tablet (
  id                        bigint auto_increment not null,
  file_name                 varchar(255),
  file_type                 varchar(255),
  size                      integer,
  is_active                 tinyint(1) default 0,
  created_at                datetime,
  modified_at               datetime,
  product_id                bigint,
  constraint pk_image_tablet primary key (id))
;

create table image_tv_banner (
  id                        bigint auto_increment not null,
  file_name                 varchar(255),
  file_type                 varchar(255),
  size                      integer,
  is_active                 tinyint(1) default 0,
  created_at                datetime,
  modified_at               datetime,
  constraint pk_image_tv_banner primary key (id))
;

create table post (
  id                        bigint auto_increment not null,
  title                     varchar(255),
  text                      TEXT,
  keywords                  TEXT,
  is_active                 tinyint(1) default 0,
  url_slug                  TEXT,
  created_at                datetime,
  modified_at               datetime,
  user_id                   bigint,
  show_main_page            tinyint(1) default 0,
  constraint pk_post primary key (id))
;

create table product (
  id                        bigint auto_increment not null,
  ptype                     bigint,
  title                     TEXT,
  desc_short                TEXT,
  desc_full                 TEXT,
  icon_id                   bigint,
  future_graphic_id         bigint,
  promo_graphic_id          bigint,
  tv_banner_id              bigint,
  market_market_id          bigint,
  is_active                 tinyint(1) default 0,
  is_soon                   tinyint(1) default 0,
  url_slug                  TEXT,
  show_main_page            tinyint(1) default 0,
  constraint pk_product primary key (id))
;

create table product_market (
  market_id                 bigint auto_increment not null,
  market_android_url        varchar(255),
  market_android_icon_url   varchar(255),
  market_android_version    varchar(255),
  market_android_is_active  tinyint(1) default 0,
  market_android_is_free    tinyint(1) default 0,
  market_android_pricing    double,
  market_ios_url            varchar(255),
  market_ios_icon_url       varchar(255),
  market_ios_version        varchar(255),
  market_ios_is_active      tinyint(1) default 0,
  market_ios_is_free        tinyint(1) default 0,
  market_ios_pricing        double,
  created_at                datetime,
  modified_at               datetime,
  constraint pk_product_market primary key (market_id))
;

create table product_video (
  id                        bigint auto_increment not null,
  video_html_code           TEXT,
  video_source              varchar(255),
  is_active                 tinyint(1) default 0,
  created_at                datetime,
  modified_at               datetime,
  product_id                bigint,
  constraint pk_product_video primary key (id))
;

create table user (
  id                        bigint auto_increment not null,
  user_name                 varchar(255),
  full_name                 varchar(255),
  email_address             varchar(255),
  pass_word                 varchar(255),
  is_active                 tinyint(1) default 0,
  constraint pk_user primary key (id))
;

create table zrg_file (
  id                        bigint auto_increment not null,
  file_name                 varchar(255),
  file_type                 varchar(255),
  file_size                 bigint,
  is_active                 tinyint(1) default 0,
  created_at                datetime,
  modified_at               datetime,
  constraint pk_zrg_file primary key (id))
;

alter table image_phone add constraint fk_image_phone_product_1 foreign key (product_id) references product (id) on delete restrict on update restrict;
create index ix_image_phone_product_1 on image_phone (product_id);
alter table image_tablet add constraint fk_image_tablet_product_2 foreign key (product_id) references product (id) on delete restrict on update restrict;
create index ix_image_tablet_product_2 on image_tablet (product_id);
alter table post add constraint fk_post_user_3 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_post_user_3 on post (user_id);
alter table product add constraint fk_product_icon_4 foreign key (icon_id) references image_icon (id) on delete restrict on update restrict;
create index ix_product_icon_4 on product (icon_id);
alter table product add constraint fk_product_futureGraphic_5 foreign key (future_graphic_id) references image_future_graphic (id) on delete restrict on update restrict;
create index ix_product_futureGraphic_5 on product (future_graphic_id);
alter table product add constraint fk_product_promoGraphic_6 foreign key (promo_graphic_id) references image_promo_graphic (id) on delete restrict on update restrict;
create index ix_product_promoGraphic_6 on product (promo_graphic_id);
alter table product add constraint fk_product_tvBanner_7 foreign key (tv_banner_id) references image_tv_banner (id) on delete restrict on update restrict;
create index ix_product_tvBanner_7 on product (tv_banner_id);
alter table product add constraint fk_product_market_8 foreign key (market_market_id) references product_market (market_id) on delete restrict on update restrict;
create index ix_product_market_8 on product (market_market_id);
alter table product_video add constraint fk_product_video_product_9 foreign key (product_id) references product (id) on delete restrict on update restrict;
create index ix_product_video_product_9 on product_video (product_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table company;

drop table image_future_graphic;

drop table image_icon;

drop table image_phone;

drop table image_promo_graphic;

drop table image_tablet;

drop table image_tv_banner;

drop table post;

drop table product;

drop table product_market;

drop table product_video;

drop table user;

drop table zrg_file;

SET FOREIGN_KEY_CHECKS=1;

