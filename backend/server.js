import express from "express";
import fetch from "node-fetch";
import cors from "cors";

const app = express();
app.use(cors());

app.get("/api/search", async (req, res) => {
  const q = req.query.q;

  const url = `https://nominatim.openstreetmap.org/search?format=jsonv2&q=${encodeURIComponent(q)}&limit=6`;

  const response = await fetch(url, {
    headers: {
      "User-Agent": "MyGeoApp/1.0"
    }
  });

  const data = await response.json();
  res.json(data);
});

app.listen(5000, () => console.log("Server running"));