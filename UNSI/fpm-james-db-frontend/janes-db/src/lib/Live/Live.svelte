<script>
  import { plan } from "../../../store";

</script>
<div id="live">
    {#if $plan=="Live"}
    <h1>hello</h1>
    {/if}
</div>
<style>
    #live {
      position:fixed;
      left:0;
      top:0;
      width:100%;
      height:100%;
    }
    </style>