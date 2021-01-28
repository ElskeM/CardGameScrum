<template>
  <div class="overall">
    <form>
      <fieldset>
        <legend>Glömt lösenord</legend>
        <div class="form">
          <input type="email" placeholder="Mejladress" />
          <button type="submit">Återställ lösenord</button>
        </div>
      </fieldset>
    </form>
  </div>
</template>

<style scoped>
.overall {
  margin-top: 30px;
}
legend {
  font-size: 18px;
}
form {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 30px;
}
input,
button {
  padding: 5px;
  border: 1px solid #ccc;
  border-radius: 4px;
  margin: 2px;
  margin-top: 10px;
  margin-bottom: 10px;
}
</style>