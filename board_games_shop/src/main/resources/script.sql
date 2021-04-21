
DROP TABLE IF EXISTS product;
create table product (
    prod_id identity primary key,
    copies varchar (10) default 10,
    prod_name varchar(500),
    thumbnail varchar(2000),
    price int,
    availability varchar(100),
    description varchar(3000),
    quantity int default 4
);

DROP TABLE IF EXISTS userr;
create table userr(
    username varchar(100) primary key,
    password varchar(500),
    address varchar(1000),
    is_account_non_expired boolean default false,
    is_account_non_locked boolean default false,
    is_credentials_non_expired boolean default false,
    is_enabled boolean default false

);

DROP TABLE IF EXISTS monopoly;
create table monopoly(
    prod_id identity primary key,
    theme varchar(500),

    foreign key (prod_id) references product
);

DROP TABLE IF EXISTS puzzle;
create table puzzle(
    prod_id identity primary key,
    number varchar(256),
    dimensions varchar(256),

    foreign key (prod_id) references product
);

DROP TABLE IF EXISTS cards;
create table cards(
   prod_id identity primary key,
   game varchar(256),

   foreign key (prod_id) references product
);

DROP TABLE IF EXISTS cart;
create table cart(
    cart_id identity primary key
);

create table roles(
    role_id int primary key,
    role_name varchar (100)
);

DROP TABLE IF EXISTS cart;
create table userr_roles(
    role_id int,
    user_id varchar(400),

    foreign key (role_id) references roles(role_id),
    foreign key (user_id) references userr(username)
);

insert into roles(role_id, role_name)
values
(1, 'ADMIN'),
(2, 'USER');

insert into userr(username, password, address, is_account_non_expired, is_account_non_locked, is_credentials_non_expired, is_enabled)
values
('borjan', '123', 'Bul. ASNOM', true,true,true,true),
('martin', '456', 'Bul. VSB',true,true,true,true);

insert into product
values(1, 3,  'GOT Monopoly', 'https://imagesvc.meredithcorp.io/v3/mm/image?q=85&c=sc&poi=face&w=2000&h=1333&url=https%3A%2F%2Fstatic.onecms.io%2Fwp-content%2Fuploads%2Fsites%2F6%2F2019%2F11%2Fgot-monopoly2-1-2000.jpg', 5300, 'Available', 'Game of Thrones meets the Fast-Dealing Property Trading Game in this Monopoly game for GOT fans. The gameboard, packaging, tokens, money, Chance cards, and game pieces are all inspired by the popular TV series from HBO. Move around the board buying as many Game of Thrones properties as you can.',1);

insert into monopoly
values(1, 'Game of Thrones');

insert into product
values(2, 3, 'Tuscan Farmhouse, Pienza, Siena', 'https://goblingames.mk/wp/wp-content/uploads/2021/03/Tuscan-Farmhouse-Pienza-Siena.jpg', 135, 'Available', 'Ravensburger has the perfect puzzle for you!
From 300 piece to the world’s-largest 40,320 piece puzzle – and everything in between – Ravensburger has something for everyone! Choose from landscapes, monuments, works of art, history, maps, animals, and more – each colorful design is constructed with our exclusive materials and specially-designed tools to ensure the highest quality puzzling experience. Once you’ve tried our extra-thick, extra-durable cardboard, glare-free linen paper, and perfect, interlocking fit, you’ll know why Ravensburger is the puzzle of choice around the world.', 2);

insert into puzzle
values(2, '1000', '70cm x 50cm');

insert into product
values(3, 4, 'Monopoly', 'https://www.pngkit.com/png/full/501-5012412_game-monopoly-hasbro-monopoly-board-game.png', 255, 'Available', 'Don''t get sad…get even! Say goodbye to the frustration of losing while playing Monopoly, and say hello to the Monopoly For Sore Losers board game. This hilarious twist on the Monopoly game turns losing on its head and celebrates it instead. Players earn cash by doing the typically frustrating things in the game such as going to Jail, paying rent on a property, or going bankrupt. And those who really start sinking can make a comeback using the large Mr. Monopoly token that gives players advantages over everyone else. So get playing, get the revenge you''ve been craving, and feel good about losing! Family Game Night might never be the same again with this game for ages 8 and up.', 2);

insert into monopoly
values(3, 'Classic Monopoly');


insert into product
values(4, 4, 'Structure Deck: Freezing Chains', 'https://goblingames.mk/wp/wp-content/uploads/2021/04/Freezing-Chains.jpg', 55, 'Available', 'Each Structure Deck: Freezing Chains includes 46 cards (41-card Main Deck + 5-card Extra Deck):

37 Commons
3 Super Rares
6 Ultra Rares
1 Double-sided Deluxe Game Mat/Dueling Guide', 2);

insert into cards
values(4, 'Yu Gi Oh!');