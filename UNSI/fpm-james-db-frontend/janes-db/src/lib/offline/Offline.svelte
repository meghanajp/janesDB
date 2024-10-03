<script>
  import { onDestroy, onMount } from "svelte";

  import { plan, frameConfig, classificationFrame, jsonData,imageUrl } from "../../../store";
  import { logSuccess } from "../../utils";
   import { Button,Dropdown,Row, Column, NumberInput } from "carbon-components-svelte"




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
<div id="offline">
    {#if $plan==="Offline"}
    
    <div
    class="frame-container" 
    bind:clientWidth={$frameConfig.CUR_W}
    bind:clientHeight={$frameConfig.CUR_H}
    
  >
    <!-- <canvas
      class="frame__canvas-overlay"
      width={$frameConfig.CUR_W}
      height={$frameConfig.CUR_H}
      bind:this={canvasEl}
    /> -->
    <div class="image-container">
      
      <div class="image-child-container">
        <p  style="font-size:30px">Image Preview:</p>
        {#if $imageUrl}
        <img src={$imageUrl} alt="Selected Image"  width={1100} height={580} />
        {:else}
        <span style="marginTop:230px">No Image Uploaded</span>
        {/if}
      </div>
      
      <!-- <div style="position: absolute; top: 18rem; left: 1rem; z-index: 2; justify-content:center; text-align:center"> -->
        
      <!-- </div> -->
        
 
    </div>
    <div class="upload-container">
    <h5>Information:</h5>
    <Row style="margin:10px 0px 3px 0px;">
      <Column>Country</Column>
      <Column><strong>India</strong></Column>
    </Row>
    <Row style="margin:10px 0px 3px 0px;">
      <Column>Camera FOV</Column>
      <Column><strong>1.23</strong></Column>
    </Row>
    <Row style="margin:10px 0px 3px 0px;">
      <Column>Radar Range</Column>
      <Column><strong>1.23</strong></Column>
    </Row>
    <Row style="margin:10px 0px 3px 0px;">
      <Column>Radar Course</Column>
      <Column><strong>1.23</strong></Column>
    </Row>
    <Row style="margin:10px 0px 3px 0px;">
      <Column>Ship Length</Column>
      <Column><strong>1.23</strong></Column>
    </Row>
    <Row style="margin:10px 0px 3px 0px;">
      <Column style="width:100%">Ship Classification</Column>
      <Column><strong>Warship</strong></Column>
    </Row>
    </div>
    
    <!-- <div class="image-container"> -->
      
    <!-- </div> -->
    
  
    
  </div>
    {/if}
</div>
<style>
    #offline {
      position:fixed;
      left:0;
      top:0;
      width:100%;
      height:100%;
    }
    .frame-container{
      display: flex;
      justify-content: space-between;
      width:100%;
      height:100%;
    }

    .upload-container {
      /* position: absolute; */
      flex-basis: 10%;
      /* border: 1px solid black; */
      background-color: bisque;
      /* width:1920px;
      height:720px; */
    }


    .image-container{
      flex-basis:90%;
      width:120em;
      text-align: center;
      justify-content: center;

      /* position: absolute; */
    }


    .image-child-container{
    position: absolute;
     top: 8rem; 
     left: 1rem; 
     z-index: 2;
     border: 4px solid grey;
     border-radius:5px;
     width:85%;
     height:80%;
     border-style: dashed;
    }

    h5{
      margin:100px;
    }

    </style>