let backend = {
  PROTOCOL:"http",
  IP:"localhost",
  PORT:8080,
};

backend.ROOT_URL = backend.PROTOCOL + "://" + backend.IP + ":" + backend.PORT;

export default backend;
