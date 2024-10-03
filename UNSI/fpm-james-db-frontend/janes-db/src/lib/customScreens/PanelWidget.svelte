<script>
      import { onDestroy, onMount } from "svelte";
import {get} from "svelte/store"

  import { plan, frameConfig, classificationFrame, jsonData,imageUrl } from "../../../store";
  import { logSuccess } from "../../utils";
  import { Button,Dropdown,Row, Column, NumberInput } from "carbon-components-svelte";
  import { ContinuousDeployment } from "carbon-icons-svelte";

let sortBySelectId = "0";
let sortByList = [
                  { id: "0", text: "INDIA" , key: "india", type:"asc"},
                  { id: "1", text: "PAKISTAN" , key:"pakistan", type:"asc" },
                  { id: "2", text: "CHINA" , key: "china", type:"asc"},
                  ];

// Function to handle file input change
function handleFileInput(event) {
  
const file = event.target.files[0];  // Get the selected file
jsonData.update(currentData=>({
  ...currentData,
  image:file
}))

if (file) {
  const reader = new FileReader();  // Create a FileReader instance

  // When the file is loaded, set the image URL
  reader.onload = function(e) {
    imageUrl.set(e.target.result);  // Store the base64 image URL
  };

  // Read the file as a data URL
  reader.readAsDataURL(file);
}
}

function handleRadarRange(e){
jsonData.update(currentData=>({
  ...currentData,
  radarRange:e.detail
}))
}

function handleRadarCourse(e){
jsonData.update(currentData=>({
  ...currentData,
  randarCourse:e.detail
}))
}

function handleCameraFov(e){
jsonData.update(currentData=>({
  ...currentData,
  cameraFov:e.detail
}))
}

async function handleSubmit(){
  const formData = new FormData();
  console.log($jsonData)
  formData.append("image", $jsonData["image"]);
  formData.append("details", JSON.stringify($jsonData));
  const res = await fetch("http://localhost:8083/janes-db/usermodule/sendImage", {
          method: 'POST',
          body:formData, 
          headers: {

          }
      });
      console.log(res.json())
      return res.json();
}

function handleDropdownSelect(e){
console.log(e)
jsonData.update(currentData=>({
  ...currentData,
  country:e.detail?.selectedItem?.key
}))
}


onMount(() => {
  logSuccess("Camera UI mounted");
  let urlParams = new URLSearchParams(window.location.search);
  let w = urlParams.get("w"),
    h = urlParams.get("h");
  if (w && h) {
    $frameConfig.SRC_W = parseInt(w);
    $frameConfig.SRC_H = parseInt(h);
  }
  // canvasCtx = canvasEl.getContext("2d");
  console.log(w,h)
});

onDestroy(() => {

});


</script>

<div class="panel-container">
   
    <div class="wrapper">
        <h6>Information Panel<h6>
            <div class="divider"></div>
        <slot></slot>
      </div>
      <Row style="margin: 10px 10px 0px 2px">
        <input type="file" accept="image/png, image/jpg"  on:change={handleFileInput} />
      </Row>
    <Row style="margin-top:20px;margin-left:10px;">
      <Column> <span class="label">COUNTRY</span> </Column>
      <Column><Dropdown bind:selectedId={sortBySelectId} items={sortByList} light size="sm"  on:select={(event)=>handleDropdownSelect(event)}/></Column>
    </Row>
    <Row style="margin-top:20px;margin-left:10px;">
      <Column> <span class="label">RADAR RANGE</span> </Column>
      <Column><NumberInput min={0} max={1000} step={1} value={0} style="max-width:3rem;padding:0 0.5rem 0 1rem; min-wdith:3rem" on:change={handleRadarRange}/></Column>
    </Row>
    <Row style="margin-top:20px;margin-left:10px;">
      <Column> <span class="label">RADAR COURSE</span> </Column>
      <Column><NumberInput min={0} max={1000} step={1} value={0} style="max-width:3rem;padding:0 0.5rem 0 1rem; min-wdith:3rem" on:change={handleRadarCourse}/></Column>
    </Row>
    <Row style="margin-top:20px;margin-left:10px;">
      <Column> <span class="label">CAMERA FOV</span> </Column>
      <Column><NumberInput min={0} max={1000} step={1} value={0} style="max-width:3rem;padding:0 0.5rem 0 1rem; min-wdith:3rem" on:change={handleCameraFov}/></Column>
    </Row>
    <Button style="margin-left:10px;margin-top:20px" on:click={handleSubmit}>SUBMIT</Button>
</div>
<style>
        .panel-container{
        display: block;
        justify-content: center;
        align-items: center;
        height: 97vh;
    }
    input{
      width: 100%;
      margin:10px -10px 0px 5px;
      border: 1px solid black;
      border-radius:5px;
      border-style: dashed;
      padding:50px;
      background-color: grey;
    }
    .divider {
    border-top: 1px solid #b1b0b0; /* Customize border color and thickness */
    margin: 0.2rem 0; /* Customize margin */
  }
  .wrapper{
    margin:10px 0px 0px 10px;
  }
  
</style>