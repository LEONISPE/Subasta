db = db.getSiblingDB('DBProducto'); // Selecciona la base de datos

db.createUser({
    user: "leonispe",
    pwd: "1234",
    roles: [
        {
            role: "readWrite",
            db: "DBProducto"
        }
    ]
});