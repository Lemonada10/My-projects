const http = require('http');
const fs = require('fs');
const path = require('path');
const url = require('url');
const querystring = require('querystring');

const server = http.createServer((req, res) => {
  const parsedUrl = url.parse(req.url);
  const pathname = parsedUrl.pathname;
  let userFound = false;

  let filePath = '.' + req.url;
  if (filePath === './') {
    filePath = './Home page.html'; 
  }


  const routes = {
    '/': './Home page.html',
    '/Home%20page.html': './Home page.html',
    '/Find%20a%20dog-cat.html': './Find a dog-cat.html',
    '/Contact%20Us.html': './Contact Us.html',
    '/Browse%20Available%20Pets.html': './Browse Available Pets.html',
    '/Dog%20Care.html': './Dog Care.html',
    '/Cat%20Care.html': './Cat Care.html',
    '/Have%20a%20pet%20to%20give%20away.html': './Have a pet to give away.html',
    '/Login.html': './Login.html',
    '/cat1.jpeg': './cat1.jpeg',
    '/cat2.jpeg': './cat2.jpeg',
    '/dog1.jpeg': './dog1.jpeg',
    '/dog2.jpeg': './dog2.jpeg',
    '/Logo.jpg': './Logo.jpg',
    '/pexels-felix-mittermeier-14045199.jpg': './pexels-felix-mittermeier-14045199.jpg',
    '/pexels-photo-58997.jpeg': './pexels-photo-58997.jpeg',
    '/pexels-pixabay-57416.jpg': './pexels-pixabay-57416.jpg',
  }

  if (routes[pathname]) {
    filePath = routes[pathname];
  }

  
  if (pathname === '/') {
    serveStaticFile('./Home page.html', res);
  }

  else if (pathname === '/findpet') {
    if (req.method === 'POST') {
      res.writeHead(302, { 'Location': '/Browse Available Pets.html' });
            res.end();
            return;
    }
  }
  
  else if (pathname === '/create-account') {
    if (req.method === 'POST') {
      let body = '';
      req.on('data', chunk => {
        body += chunk.toString();
      });
      req.on('end', () => {
        const formData = querystring.parse(body);
        const username = formData.username;
        const password = formData.password;

        console.log('Parsed username:', username);
        console.log('Parsed password:', password);

       
        fs.readFile('./login.txt', 'utf8', (err, data) => {
          if (err) {
            res.writeHead(500);
            res.end('Internal Server Error');
            return;
          }

          
          const users = data.split('\n');
          for (const user of users) {
            const [existingUsername] = user.split(':');
            if (existingUsername === username) {
              res.writeHead(400);
              res.end('<script>alert("Username already exists. Please choose a different username."); window.history.back(); </script>');
              return;
            }
          }

          
          console.log(`Appending username: '${username}', password: '${password}' to login.txt`);
          fs.appendFile('./login.txt', `${username}:${password}\n`, err => {
            if (err) {
              console.error('Error appending to login.txt:', err);
              res.writeHead(500);
              res.end('Internal Server Error');
              return;
            }
            console.log(`Username '${username}' and password appended to login.txt successfully.`);
            res.writeHead(200);
            res.end('<script>alert("Account created successfully. You can now login."); window.history.back(); </script>');
            return;

          });
        });
      });
    }
  } else if (pathname === '/give_away') {
    if (req.method === 'POST') {
      
        if (!req.headers.cookie || !req.headers.cookie.includes('loggedIn=true')) {
          
          res.end('<script>alert("You need to login first."); window.history.back(); </script>');
          return;
      }
      else {

        let body = '';
        req.on('data', chunk => {
          body += chunk.toString();
        });
        req.on('end', () => {
          
          const formData = querystring.parse(body);
          const petInfo = formatPetInfo(formData);

          
          fs.appendFile('./available-pets.txt', `${petInfo}\n`, err => {
            if (err) {
              res.writeHead(500);
              res.end('Internal Server Error');
              return;
            }
            res.writeHead(200);
            res.end('<script>alert("Pet information added successfully."); window.history.back(); </script>')
          });
        });
      }
    }
  } else if (pathname === '/login') {

    if (req.method === 'POST') {
      let body = '';
      req.on('data', chunk => {
        body += chunk.toString();
      });
      req.on('end', () => {
        const formData = querystring.parse(body);
        const username = formData.username;
        const password = formData.password;

        
        fs.readFile('./login.txt', 'utf8', (err, data) => {
          if (err) {
            res.writeHead(500);
            res.end('Internal Server Error');
            return;
          }

          
          const users = data.split('\n');
          userFound = false;
          for (const user of users) {
            const [existingUsername, existingPassword] = user.split(':');
            if (existingUsername === username && existingPassword === password) {
             

              userFound = true; 
              console.log('User found after successful login:', userFound); 
              break;
            }
          }

          console.log('User found before sending response:', userFound);

          if (userFound) {
            
            res.setHeader('Set-Cookie', 'loggedIn=true; HttpOnly');
            
            res.end('<script>alert("Succesfully connected"); window.history.back(); </script>');
        } else {
            
            res.writeHead(200, { 'Content-Type': 'text/html' });
            res.end('<script>alert("Incorrect username or password."); window.history.back(); </script>');
          }
        });
      });
    }
  } else if (pathname === '/logout') {
    
    res.setHeader('Set-Cookie', 'loggedIn=; HttpOnly; Max-Age=0');
    
    res.end('<script>alert("Succesfully logged out"); window.history.back(); </script>');
} else {
   
    serveStaticFile(filePath, res);
  }
});

function serveStaticFile(filePath, res) {
  fs.readFile(filePath, (err, content) => {
    if (err) {
      if (err.code === 'ENOENT') {
        
        serve404Page(res);
      } else {
        
        res.writeHead(500);
        res.end(`Server Error: ${err.code}`);
      }
    } else {
      
      let contentType = 'text/html';
      const extname = path.extname(filePath);
      switch (extname) {
        case '.js':
          contentType = 'text/javascript';
          break;
        case '.css':
          contentType = 'text/css';
          break;
        case '.jpg':
        case '.jpeg':
          contentType = 'image/jpeg';
          break;
        case '.png':
          contentType = 'image/png';
          break;
      }

      
      res.writeHead(200, { 'Content-Type': contentType });
      res.end(content, 'utf-8');
    }
  });
}

function serve404Page(res) {
  fs.readFile('./404.html', (err, content) => {
    if (err) {
      res.writeHead(500, { 'Content-Type': 'text/html' });
      res.end('Server Error: Unable to load the 404 page.');
    } else {
      res.writeHead(404, { 'Content-Type': 'text/html' });
      res.end(content, 'utf-8');
    }
  });
}

function formatPetInfo(formData) {
 
  const firstname = formData.FirstName;
  const lastname = formData.LastName;
  const animal = formData.animal;
  const age = formData.age;
  const gender = formData.gender;

  return `${firstname}:${lastname}:${animal}:${age}:${gender}`;
}

const PORT = process.env.PORT || 5242;
server.listen(PORT, () => {
  console.log(`Server running on port http://localhost:${PORT}`);
});