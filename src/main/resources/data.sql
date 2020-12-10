INSERT INTO hall (name,places)
VALUES ('Default Hall',
    REPLACE('[[{"id":1,"row":1,"position":1,"hall_id":1,"cost":300,"account_id":0,"occupied":false},
         {"id":2,"row":1,"position":2,"hall_id":1,"cost":200,"account_id":0,"occupied":false},
         {"id":3,"row":1,"position":3,"hall_id":1,"cost":300,"account_id":0,"occupied":false}
        ],
        [{"id":4,"row":2,"position":1,"hall_id":1,"cost":500,"account_id":0,"occupied":false},
         {"id":5,"row":2,"position":2,"hall_id":1,"cost":500,"account_id":0,"occupied":false},
         {"id":6,"row":2,"position":3,"hall_id":1,"cost":500,"account_id":0,"occupied":false}
        ],
        [{"id":7,"row":3,"position":1,"hall_id":1,"cost":700,"account_id":0,"occupied":false},
         {"id":8,"row":3,"position":2,"hall_id":1,"cost":700,"account_id":0,"occupied":false},
         {"id":9,"row":3,"position":3,"hall_id":1,"cost":700,"account_id":0,"occupied":false}]]',' ',''))