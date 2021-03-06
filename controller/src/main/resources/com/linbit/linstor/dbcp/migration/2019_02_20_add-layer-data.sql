CREATE TABLE LAYER_RESOURCE_IDS
(
    LAYER_RESOURCE_ID INTEGER NOT NULL,
    NODE_NAME VARCHAR(255) NOT NULL,
    RESOURCE_NAME VARCHAR(48) NOT NULL,
    LAYER_RESOURCE_KIND VARCHAR(100) NOT NULL,
    LAYER_RESOURCE_PARENT_ID INTEGER NULL,  -- nullable
    LAYER_RESOURCE_SUFFIX VARCHAR(255) NOT NULL,
    CONSTRAINT PK_LRI PRIMARY KEY (LAYER_RESOURCE_ID),
    CONSTRAINT FK_LRI_RESOURCES FOREIGN KEY (NODE_NAME, RESOURCE_NAME) REFERENCES RESOURCES(NODE_NAME, RESOURCE_NAME) ON DELETE CASCADE
);

CREATE TABLE LAYER_DRBD_RESOURCES
(
    LAYER_RESOURCE_ID INTEGER NOT NULL,
    PEER_SLOTS INTEGER NOT NULL,
    AL_STRIPES INTEGER NOT NULL,
    AL_STRIPE_SIZE BIGINT NOT NULL,
    FLAGS BIGINT NOT NULL,
    NODE_ID INTEGER NOT NULL,
    CONSTRAINT PK_LDR PRIMARY KEY (LAYER_RESOURCE_ID),
    CONSTRAINT FK_LDR_LRI FOREIGN KEY (LAYER_RESOURCE_ID) REFERENCES LAYER_RESOURCE_IDS(LAYER_RESOURCE_ID) ON DELETE CASCADE
);

CREATE TABLE LAYER_DRBD_RESOURCE_DEFINITIONS
(
    RESOURCE_NAME VARCHAR(48) NOT NULL,
    RESOURCE_NAME_SUFFIX VARCHAR(48) NOT NULL,
    PEER_SLOTS INTEGER NOT NULL,
    AL_STRIPES INTEGER NOT NULL,
    AL_STRIPE_SIZE BIGINT NOT NULL,
    TCP_PORT INTEGER NOT NULL,
    TRANSPORT_TYPE VARCHAR(40) NOT NULL,
    SECRET VARCHAR(20) NOT NULL,

    CONSTRAINT PK_LDRD PRIMARY KEY (RESOURCE_NAME, RESOURCE_NAME_SUFFIX),
    CONSTRAINT FK_LDRD_RD FOREIGN KEY (RESOURCE_NAME) REFERENCES RESOURCE_DEFINITIONS(RESOURCE_NAME) ON DELETE CASCADE
);

CREATE TABLE LAYER_DRBD_VOLUME_DEFINITIONS
(
    RESOURCE_NAME VARCHAR(48) NOT NULL,
    RESOURCE_NAME_SUFFIX VARCHAR(48) NOT NULL,
    VLM_NR INTEGER NOT NULL,
    VLM_MINOR_NR INTEGER NOT NULL,

    CONSTRAINT PK_LDVD PRIMARY KEY (RESOURCE_NAME, RESOURCE_NAME_SUFFIX, VLM_NR),
    CONSTRAINT FK_LDVD_VD FOREIGN KEY (RESOURCE_NAME, VLM_NR) REFERENCES VOLUME_DEFINITIONS(RESOURCE_NAME, VLM_NR) ON DELETE CASCADE,
    CONSTRAINT UNQ_LDVD_MINOR UNIQUE (VLM_MINOR_NR)
);

CREATE TABLE LAYER_LUKS_VOLUMES
(
    LAYER_RESOURCE_ID INTEGER NOT NULL,
    VLM_NR INTEGER NOT NULL,
    ENCRYPTED_PASSWORD VARCHAR(4096) NOT NULL, -- encrypted and base64 encoded. previously stored in PROPS_CONTAINER as PROP_VALUE
    CONSTRAINT PK_LCSV PRIMARY KEY (LAYER_RESOURCE_ID, VLM_NR),
    CONSTRAINT FK_LCSV_LRI FOREIGN KEY (LAYER_RESOURCE_ID) REFERENCES LAYER_RESOURCE_IDS(LAYER_RESOURCE_ID) ON DELETE CASCADE
);

CREATE TABLE LAYER_SWORDFISH_VOLUME_DEFINITIONS
(
    RESOURCE_NAME VARCHAR(48) NOT NULL,
    RESOURCE_NAME_SUFFIX VARCHAR(48) NOT NULL,
    VLM_NR INTEGER NOT NULL,
    VLM_ODATA VARCHAR(4096) NULL, -- nullable
    CONSTRAINT PK_LSVD PRIMARY KEY (RESOURCE_NAME, RESOURCE_NAME_SUFFIX, VLM_NR),
    CONSTRAINT FK_LSFVD_VD FOREIGN KEY (RESOURCE_NAME, VLM_NR) REFERENCES VOLUME_DEFINITIONS(RESOURCE_NAME, VLM_NR) ON DELETE CASCADE
);

CREATE TABLE LAYER_STORAGE_VOLUMES
(
    LAYER_RESOURCE_ID INTEGER NOT NULL,
    VLM_NR INTEGER NOT NULL,
    PROVIDER_KIND VARCHAR(100) NOT NULL,

    CONSTRAINT PK_LSV PRIMARY KEY (LAYER_RESOURCE_ID, VLM_NR),
    CONSTRAINT FK_LSV_LRI FOREIGN KEY (LAYER_RESOURCE_ID) REFERENCES LAYER_RESOURCE_IDS(LAYER_RESOURCE_ID) ON DELETE CASCADE
);

