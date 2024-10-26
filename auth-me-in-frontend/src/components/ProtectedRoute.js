import React from "react";
import { Navigate, useLocation } from "react-router-dom";

function ProtectedRoute({ children }) {
  const token = localStorage.getItem("token");
  const location = useLocation();

  if (!token) {
    return (
      <Navigate
        to="/login"
        state={{ from: location, message: "You must log in to continue" }}
        replace
      />
    );
  }

  return children;
}

export default ProtectedRoute;
