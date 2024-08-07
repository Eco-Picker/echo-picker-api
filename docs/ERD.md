# ERD (Entity Relationship Diagram)

### Final version of ERD image will be uploaded here

## Tables

### User
- **user_id**: String (PK)
- **email**: String
- **password**: String
- **onboarding_status**: Int
- **created_at**: ZonedDateTime
- **updated_at**: ZonedDateTime

### Garbage
- **id**: Long (PK)
- **user_id**: String (FK)
- **garbage_type**: String
- **garbage_detail**: String
- **collected_latitude**: Double
- **collected_longitude**: Double
- **collected_time**: ZonedDateTime
- **garbage_image_url**: String
- **created_at**: ZonedDateTime
- **updated_at**: ZonedDateTime

### Auth
- **user_id**: String (PK, FK)
- **access_token**: String
- **refresh_token**: String
- **created_at**: ZonedDateTime
- **updated_at**: ZonedDateTime

### GarbageMonthly
- **id**: Long (PK)
- **user_id**: String (FK)
- **month**: String
- **plastic**: Int
- **metal**: Int
- **glass**: Int
- **cardboard_paper**: Int
- **food_scraps**: Int
- **other**: Int
- **created_at**: ZonedDateTime
- **updated_at**: ZonedDateTime