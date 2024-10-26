import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import "./Register.css";

import { API_BASE_URL } from "../../config";

function Register() {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [errors, setErrors] = useState({});
  const navigate = useNavigate();
  const token = localStorage.getItem("token");

  useEffect(() => {
    if (token) {
      navigate("/"); // Redirect to home if already logged in
    }
  }, [token, navigate]);

  const validateForm = () => {
    const newErrors = {};

    // Required field validation
    if (!name.trim()) {
      newErrors.name = "Name is required";
    }
    if (!email.trim()) {
      newErrors.email = "Email is required";
    } else if (!/\S+@\S+\.\S+/.test(email)) {
      newErrors.email = "Invalid email format";
    }
    if (!username.trim()) {
      newErrors.username = "Username is required";
    }
    if (!password) {
      newErrors.password = "Password is required";
    }
    if (!confirmPassword) {
      newErrors.confirmPassword = "Confirm password is required";
    }

    if (name && !/^[A-Za-z\s]+$/.test(name)) {
      newErrors.name = "Name should only contain letters and spaces";
    }
    if (username && !/^[A-Za-z][A-Za-z0-9]*$/.test(username)) {
      newErrors.username =
        "Username should start with a letter and contain only letters and numbers";
    }

    if (!password) {
      newErrors.password = "Password is required";
    } else if (password.length < 8) {
      newErrors.password = "Password should be at least 8 characters long";
    } else if (
      !/^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/.test(
        password
      )
    ) {
      newErrors.password =
        "Password should contain at least one letter, one number, and one special character";
    }
    if (password && confirmPassword && password !== confirmPassword) {
      newErrors.confirmPassword = "Passwords do not match";
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (validateForm()) {
      try {
        const response = await fetch(`${API_BASE_URL}/auth/register`, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ name, email, username, password }),
        });

        const data = await response.json();

        if (data.success) {
          toast.success("Registration successful!");
          localStorage.setItem("token", data.payload);
          navigate("/login");
        } else {
          toast.error(data.payload);
        }
      } catch (error) {
        console.error("Registration error:", error);
        toast.error("An error occurred during registration. Please try again.");
      }
    }
  };

  return (
    <div className="register-container">
      <form className="register-form" onSubmit={handleSubmit}>
        <h2 className="register-title">Register</h2>

        <div className="form-group">
          <input
            type="text"
            id="name"
            name="name"
            placeholder=""
            value={name}
            onChange={(e) => setName(e.target.value)}
            required
          />
          <label htmlFor="username">Full Name</label>
          {errors.name && <span className="error-message">{errors.name}</span>}
        </div>

        <div className="form-group">
          <input
            type="text"
            id="email"
            name="email"
            placeholder=""
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
          <label htmlFor="email">Email</label>
          {errors.email && (
            <span className="error-message">{errors.email}</span>
          )}
        </div>

        <div className="form-group">
          <input
            type="text"
            id="username"
            name="username"
            placeholder=""
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
          <label htmlFor="username">Username</label>
          {errors.username && (
            <span className="error-message">{errors.username}</span>
          )}
        </div>

        <div className="form-group">
          <input
            type="password"
            id="password"
            name="password"
            placeholder=""
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
          <label htmlFor="username">Password</label>
          {errors.password && (
            <span className="error-message">{errors.password}</span>
          )}
        </div>

        <div className="form-group">
          <input
            type="password"
            id="password"
            name="password"
            placeholder=""
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
            required
          />
          <label htmlFor="username">Confirm Password</label>
          {errors.confirmPassword && (
            <span className="error-message">{errors.confirmPassword}</span>
          )}
        </div>

        <div className="form-button">
          <button type="submit" className="register-button">
            Register
          </button>
        </div>

        <div className="form-footer">
          <p className="login-prompt">
            Already have an account? <a href="/login">Login</a>
          </p>
        </div>
      </form>
    </div>
  );
}

export default Register;
